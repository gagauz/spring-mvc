package org.repetitor.services.template;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.repetitor.database.dao.PageTemplateDao;
import org.repetitor.database.model.Message;
import org.repetitor.database.model.MessageTemplate;
import org.repetitor.database.model.PageTemplate;
import org.repetitor.utils.SysEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.cache.TemplateLoader;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.NumberModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@Service
public class TemplateService {
    private static final Logger LOG = LoggerFactory.getLogger(TemplateService.class);

    private static final String SUFFIX = ".ftl";
    private final File templatePath;
    private final Configuration fileCfg;
    private final Configuration databaseCfg;

    @Autowired
    private PageTemplateDao templateDao;

    private ObjectProxy objectProxy = new ObjectProxy();

    public TemplateService() throws IOException {
        templatePath = new File(SysEnv.PAGE_TEMPLATE_DIR.toString());
        fileCfg = new Configuration();
        fileCfg.setDirectoryForTemplateLoading(templatePath);
        fileCfg.setDefaultEncoding("UTF-8");
        fileCfg.setNumberFormat("0.######");
        fileCfg.setObjectWrapper(new DefaultObjectWrapper());

        // --

        databaseCfg = new Configuration();
        databaseCfg.setDefaultEncoding("UTF-8");
        databaseCfg.setNumberFormat("0.######");
        databaseCfg.setObjectWrapper(new NormalObjectWraper());
        databaseCfg.setTemplateLoader(new ProxyTemplateLoader(objectProxy));
    }

    protected String createContentFromFile(final String name, Map<String, Object> rootMap) {

        try {

            final Template template = fileCfg.getTemplate(name + SUFFIX);

            return processTemplate(rootMap, template);
        } catch (Exception e) {
            LOG.error("Exception while merging template " + name, e);
            return createContentFromDB(name, rootMap);
        }

    }

    protected String createContentFromDB(final String name, Map<String, Object> rootMap) {

        try {
            synchronized (objectProxy) {

                PageTemplate template = templateDao.findByName(name);

                objectProxy.setObject(template, rootMap);
                Template subjTemplate = databaseCfg.getTemplate(type + SUBJ_SUFFIX);
                Template bodyTemplate = databaseCfg.getTemplate(type + BODY_SUFFIX);
                mesage.setType(template.getType());
                processTemplate(mesage, rootMap, subjTemplate, bodyTemplate);
                objectProxy.setObject(null, null);

                saveTemplateToFile(template);

                return mesage;
            }
        } catch (Exception e) {
            LOG.error("Exception while merging template " + type, e);
            e.printStackTrace();
            throw new IllegalStateException("Failed to create template for " + type);
        }
    }

    protected String createContentFromDB(final String name, Map<String, Object> rootMap) {

        try {
            synchronized (objectProxy) {

                MessageTemplate template = messageTemplateDao.findByType(type, isSms);

                objectProxy.setObject(template, rootMap);
                Template subjTemplate = databaseCfg.getTemplate(type + SUBJ_SUFFIX);
                Template bodyTemplate = databaseCfg.getTemplate(type + BODY_SUFFIX);
                mesage.setType(template.getType());
                processTemplate(mesage, rootMap, subjTemplate, bodyTemplate);
                objectProxy.setObject(null, null);

                saveTemplateToFile(template);

                return mesage;
            }
        } catch (Exception e) {
            LOG.error("Exception while merging template " + type, e);
            e.printStackTrace();
            throw new IllegalStateException("Failed to create template for " + type);
        }
    }

    public void saveTemplateToFile(MessageTemplate template) throws IOException {
        final String prefix = template.isSms() ? SMS_PREFIX : MAIL_PREFIX;
        File subjectTemplateFile = new File(templatePath, prefix + template.getType().name()
                + SUBJ_SUFFIX);
        FileUtils.writeStringToFile(subjectTemplateFile, template.getSubjectTemplate());
        File bodyTemplateFile = new File(templatePath, prefix + template.getType().name()
                + BODY_SUFFIX);
        FileUtils.writeStringToFile(bodyTemplateFile, template.getBodyTemplate());
    }

    private void processTemplate(Message mesage, Map<String, Object> rootMap,
            Template subjTemplate, Template bodyTemplate) throws TemplateException,
            IOException {
        mesage.setSubject(processTemplate(rootMap, subjTemplate));
        mesage.setBody(processTemplate(rootMap, bodyTemplate));
    }

    private String processTemplate(Map<String, Object> rootMap, Template template)
            throws TemplateException,
            IOException {
        Writer writer = new StringWriter();
        template.process(rootMap, writer);
        return writer.toString();
    }

    /**
     * Simple proxy object which will provide string template each time last
     * modified is changed. TODO May be used not only for MarketGenerator
     */
    class ObjectProxy {
        private PageTemplate proxyTemplate;
        private Map<String, Object> proxyMap;

        public long getLastModification(Object templateSource) {
            return proxyTemplate.getUpdated().getTime();
        }

        public String getTemplateName() {
            return proxyTemplate.getName();
        }

        public Map<String, Object> getProxyMap() {
            return proxyMap;
        }

        public void setObject(PageTemplate template, Map<String, Object> rootMap) {
            proxyTemplate = template;
            proxyMap = rootMap;
        }

        public String getTemplate() {
            return proxyTemplate.getTemplate();
        }

    }

    class ProxyTemplateLoader implements TemplateLoader {

        private final ObjectProxy objectProxy;

        ProxyTemplateLoader(ObjectProxy provider) {
            objectProxy = provider;
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            if (name.startsWith(objectProxy.getTemplateName())) {
                // return current proxy object
                return objectProxy.getTemplate();
            } else {
            // Handle includes
            try {
                Template template = fileCfg.getTemplate(name);
                return processTemplate(objectProxy.getProxyMap(), template);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getLastModified(Object templateSource) {
            return objectProxy.getLastModification(templateSource);
        }

        /**
         * Reader is cached.
         */
        @Override
        public Reader getReader(Object templateObject, String encoding) throws IOException {
            return new StringReader((String) templateObject);
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {
        }
    }

    class NormalObjectWraper extends DefaultObjectWrapper {
        @Override
        public TemplateModel wrap(Object obj) throws TemplateModelException {
            if (obj instanceof BigDecimal) {
                return new NumberModel((BigDecimal) obj, this);
            }
            if (obj instanceof Collection) {
                return new CollectionModel((Collection) obj, this);
            }
            return super.wrap(obj);
        }
    }

}
