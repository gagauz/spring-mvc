package org.repetitor.web.context;

import javax.servlet.ServletContext;

import org.repetitor.database.setup.DBSetup;
import org.repetitor.test.setup.TestSetup;
import org.repetitor.utils.SysEnv;
import org.repetitor.web.setup.WebSetup;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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
