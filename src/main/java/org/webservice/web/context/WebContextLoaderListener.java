package org.webservice.web.context;

import org.webservice.config.ScenariosConfig;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.webservice.database.config.DatabaseConfig;
import org.webservice.utils.SysEnv;
import org.webservice.web.config.WebConfig;
import org.webservice.web.config.WebSecurityConfig;

public class WebContextLoaderListener extends ContextLoaderListener {
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		System.out.println(servletContext.getInitParameter("contextConfigLocation"));

		String configs = WebSecurityConfig.class.getName() + "," + WebConfig.class.getName() + "," + DatabaseConfig.class.getName();

		if (SysEnv.PRODUCTION_MODE.get().toBool()) {
		} else {
			configs += "," + ScenariosConfig.class.getName();
		}

		ServletContextWrapper wrapper = new ServletContextWrapper(servletContext);
		wrapper.setInitParameter("contextConfigLocation", configs);
		wrapper.setInitParameter("contextClass",
				AnnotationConfigWebApplicationContext.class.getName());

		return super.initWebApplicationContext(wrapper);
	}
}
