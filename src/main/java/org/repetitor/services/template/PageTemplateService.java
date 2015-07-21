package org.repetitor.services.template;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.repetitor.database.dao.PageTemplateDao;
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
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@Service
public class PageTemplateService {
	private static final Logger LOG = LoggerFactory.getLogger(PageTemplateService.class);

	private static final String SUFFIX = ".ftl";
	private final File templatePath;
	private final Configuration databaseCfg;

	@Autowired
	private PageTemplateDao templateDao;

	public PageTemplateService() throws IOException {
		templatePath = new File(SysEnv.PAGE_TEMPLATE_DIR.toString());
		databaseCfg = new Configuration();
		databaseCfg.setDefaultEncoding("UTF-8");
		databaseCfg.setNumberFormat("0.######");
		databaseCfg.setObjectWrapper(new NormalObjectWraper());
		databaseCfg.setTemplateLoader(new CustomTemplateLoader(templatePath));
		databaseCfg.setLocalizedLookup(false);
	}

	public void parseTemplate(final String name, Map<String, ?> rootMap, Writer writer) {
		try {
			final Template template = databaseCfg.getTemplate(name);
			template.process(rootMap, writer);
		} catch (Exception e) {
			LOG.error("Exception while merging template " + name, e);
			throw new IllegalStateException(e);
		}
	}

	class CustomTemplateLoader implements TemplateLoader {

		private final File baseDir;

		CustomTemplateLoader(File baseDir) throws IOException {
			this.baseDir = baseDir;
		}

		@Override
		public Object findTemplateSource(String name) throws IOException {
			String fileName = assureWithExtension(name);
			File file = new File(baseDir, fileName);
			if (!file.exists()) {
				PageTemplate tpl = templateDao.findByName(assureWithoutExtension(name));
				if (null == tpl) {
					throw new IOException("Template " + name + " is not found!");
				}
				saveTemplateToFile(fileName, tpl.getTemplate());
				return tpl;
			}
			return file;
		}

		@Override
		public long getLastModified(Object templateSource) {
			if (templateSource instanceof PageTemplate) {
				if (null != ((PageTemplate) templateSource).getUpdated()) {
					return ((PageTemplate) templateSource).getUpdated().getTime();
				}
				return ((PageTemplate) templateSource).getCreated().getTime();
			}
			return ((File) templateSource).lastModified();
		}

		/**
		 * Reader is cached.
		 */
		@Override
		public Reader getReader(Object templateObject, String encoding) throws IOException {
			if (templateObject instanceof PageTemplate) {
				return new StringReader(((PageTemplate) templateObject).getTemplate());
			}
			return new FileReader(((File) templateObject));
		}

		@Override
		public void closeTemplateSource(Object templateSource) throws IOException {
		}

		private String assureWithExtension(String fileName) {
			if (!fileName.endsWith(SUFFIX)) {
				return fileName + SUFFIX;
			}
			return fileName;
		}

		private String assureWithoutExtension(String fileName) {
			if (!fileName.endsWith(SUFFIX)) {
				return fileName;
			}
			return fileName.substring(0, fileName.length() - SUFFIX.length());
		}

		private void saveTemplateToFile(String name, String template) throws IOException {
			File templateFile = new File(templatePath, name);
			FileUtils.writeStringToFile(templateFile, template);
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
