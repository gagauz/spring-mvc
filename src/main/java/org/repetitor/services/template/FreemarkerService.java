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
import org.repetitor.database.dao.MessageTemplateDao;
import org.repetitor.database.model.Mail;
import org.repetitor.database.model.Message;
import org.repetitor.database.model.MessageTemplate;
import org.repetitor.database.model.Sms;
import org.repetitor.database.model.enums.MessageType;
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
public class FreemarkerService {
	private static final Logger LOG = LoggerFactory.getLogger(FreemarkerService.class);

	private static final String SMS_PREFIX = "sms_";
	private static final String MAIL_PREFIX = "mail_";
	private static final String SUBJ_SUFFIX = "_subj.ftl";
	private static final String BODY_SUFFIX = "_body.ftl";
	private final File templatePath;
	private final Configuration fileCfg;
	private final Configuration databaseCfg;

	@Autowired
	private MessageTemplateDao messageTemplateDao;

	private final ObjectProxy objectProxy = new ObjectProxy();

	public FreemarkerService() throws IOException {
		templatePath = new File(SysEnv.MAIL_TEMPLATE_DIR.toString());
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

	public Mail createEmailContent(MessageType type, Map<String, Object> map) {
		return createMessageContentFromFile(new Mail(), type, map);
	}

	public Sms createSmsContent(MessageType type, Map<String, Object> map) {
		return createMessageContentFromFile(new Sms(), type, map);
	}

	protected <T extends Message> T createMessageContentFromFile(final T mesage,
			final MessageType type, Map<String, Object> rootMap) {
		try {

			final String prefix = mesage instanceof Sms ? SMS_PREFIX : MAIL_PREFIX;

			final Template subjTemplate = fileCfg.getTemplate(prefix + type.name() + SUBJ_SUFFIX);

			final Template bodyTemplate = fileCfg.getTemplate(prefix + type.name() + BODY_SUFFIX);

			mesage.setType(type);

			processTemplate(mesage, rootMap, subjTemplate, bodyTemplate);

			return mesage;
		} catch (Exception e) {
			LOG.error("Exception while merging template " + type, e);
			return createMessageContentFromDB(mesage, type, rootMap);
		}
	}

	protected <T extends Message> T createMessageContentFromDB(final T mesage,
			final MessageType type, Map<String, Object> rootMap) {

		try {
			synchronized (objectProxy) {

				final boolean isSms = mesage instanceof Sms;

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
		private MessageTemplate proxyTemplate;
		private Map<String, Object> proxyMap;

		public long getLastModification(Object templateSource) {
			return proxyTemplate.getUpdated().getTime();
		}

		public String getTemplateName() {
			return proxyTemplate.getType().name();
		}

		public Map<String, Object> getProxyMap() {
			return proxyMap;
		}

		public void setObject(MessageTemplate template, Map<String, Object> rootMap) {
			proxyTemplate = template;
			proxyMap = rootMap;
		}

		public String getSubjectTemplate() {
			return proxyTemplate.getSubjectTemplate();
		}

		public String getBodyTemplate() {
			return proxyTemplate.getBodyTemplate();
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
				if (name.contains(BODY_SUFFIX))
					return objectProxy.getBodyTemplate();
				if (name.contains(SUBJ_SUFFIX))
					return objectProxy.getSubjectTemplate();
				return null;
			}
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
