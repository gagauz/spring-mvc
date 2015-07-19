package org.repetitor.web.setup.context;

import javax.servlet.ServletContext;

import org.repetitor.test.setup.TestSetup;
import org.repetitor.utils.SysEnv;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class ContextLoaderListenerOverride extends ContextLoaderListener {
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        System.out.println(servletContext.getInitParameter("contextConfigLocation"));

        if (!SysEnv.PRODUCTION_MODE.get().toBool()) {
            String configs = servletContext.getInitParameter("contextConfigLocation");
            configs += ", " + TestSetup.class.getName();
            ServletContextWrapper wrapper = new ServletContextWrapper(servletContext);
            wrapper.setInitParameter("contextConfigLocation", configs);
            servletContext = wrapper;
        }

        return super.initWebApplicationContext(servletContext);
    }
}
