package org.repetitor.web.setup;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.gagauz.utils.C;
import org.repetitor.test.setup.TestSetup;
import org.repetitor.utils.SysEnv;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class ContextLoaderListener2 extends ContextLoaderListener {
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        System.out.println(servletContext.getInitParameter("contextConfigLocation"));

        if ("false".equals(SysEnv.PRODUCTION_MODE.toString())) {
            String configs = servletContext.getInitParameter("contextConfigLocation");
            configs += ", " + TestSetup.class.getName();
            ServletContextWrapper wrapper = new ServletContextWrapper(servletContext);
            wrapper.setInitParameter("contextConfigLocation", configs);
            servletContext = wrapper;
        }

        return super.initWebApplicationContext(servletContext);
    }

    class ServletContextWrapper implements ServletContext {
        private final ServletContext original;

        private final Map<String, String> initParam = C.newHashMap();

        ServletContextWrapper(ServletContext source) {
            this.original = source;
        }

        @Override
        public String getContextPath() {
            return original.getContextPath();
        }

        @Override
        public ServletContext getContext(String uripath) {
            return original.getContext(uripath);
        }

        @Override
        public int getMajorVersion() {
            return original.getMajorVersion();
        }

        @Override
        public int getMinorVersion() {
            return original.getMinorVersion();
        }

        @Override
        public String getMimeType(String file) {
            return original.getMimeType(file);
        }

        @Override
        public Set getResourcePaths(String path) {
            return original.getResourcePaths(path);
        }

        @Override
        public URL getResource(String path) throws MalformedURLException {
            return original.getResource(path);
        }

        @Override
        public InputStream getResourceAsStream(String path) {
            return original.getResourceAsStream(path);
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return original.getRequestDispatcher(path);
        }

        @Override
        public RequestDispatcher getNamedDispatcher(String name) {
            return original.getNamedDispatcher(name);
        }

        @Override
        public Servlet getServlet(String name) throws ServletException {
            return original.getServlet(name);
        }

        @Override
        public Enumeration getServlets() {
            return original.getServlets();
        }

        @Override
        public Enumeration getServletNames() {
            return original.getServletNames();
        }

        @Override
        public void log(String msg) {
            original.log(msg);
        }

        @Override
        public void log(Exception exception, String msg) {
            original.log(exception, msg);
        }

        @Override
        public void log(String message, Throwable throwable) {
            original.log(message, throwable);
        }

        @Override
        public String getRealPath(String path) {
            return original.getRealPath(path);
        }

        @Override
        public String getServerInfo() {
            return original.getServerInfo();
        }

        @Override
        public String getInitParameter(String name) {
            String param = initParam.get(name);
            if (null != param) {
                return param;
            }
            return original.getInitParameter(name);
        }

        public void setInitParameter(String name, String value) {
            this.initParam.put(name, value);
        }

        @Override
        public Enumeration getInitParameterNames() {
            final Enumeration source = original.getInitParameterNames();
            final Iterator iterator = initParam.keySet().iterator();
            return new Enumeration() {

                @Override
                public boolean hasMoreElements() {
                    return source.hasMoreElements() || iterator.hasNext();
                }

                @Override
                public Object nextElement() {
                    return source.hasMoreElements()
                            ? source.nextElement()
                            : iterator.next();
                }
            };
        }

        @Override
        public Object getAttribute(String name) {
            return original.getAttribute(name);
        }

        @Override
        public Enumeration getAttributeNames() {
            return original.getAttributeNames();
        }

        @Override
        public void setAttribute(String name, Object object) {
            original.setAttribute(name, object);
        }

        @Override
        public void removeAttribute(String name) {
            original.removeAttribute(name);
        }

        @Override
        public String getServletContextName() {
            return original.getServletContextName();
        }

    }
}
