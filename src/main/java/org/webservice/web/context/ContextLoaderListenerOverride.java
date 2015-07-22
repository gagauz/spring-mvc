package org.webservice.web.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.webservice.database.setup.DBSetup;
import org.webservice.test.setup.TestSetup;
import org.webservice.utils.SysEnv;
import org.webservice.web.setup.WebSetup;

public class ContextLoaderListenerOverride extends ContextLoaderListener {
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        System.out.println(servletContext.getInitParameter("contextConfigLocation"));

        String configs = WebSetup.class.getName() + "," + DBSetup.class.getName();

        if (SysEnv.PRODUCTION_MODE.get().toBool()) {
        } else {
            configs += "," + TestSetup.class.getName();
        }

        ServletContextWrapper wrapper = new ServletContextWrapper(servletContext);
        wrapper.setInitParameter("contextConfigLocation", configs);
        wrapper.setInitParameter("contextClass",
                AnnotationConfigWebApplicationContext.class.getName());

        return super.initWebApplicationContext(wrapper);
    }
}
