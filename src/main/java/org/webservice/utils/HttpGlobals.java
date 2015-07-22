package org.webservice.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class HttpGlobals {
    private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();
    private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

    public static void setup(HttpServletRequest request, HttpServletResponse response) {
        /*
         * Make wrapper to alter getRemoteAddr() method as we proxy requests to
         * tomcat from load-balancer.
         */
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String getRemoteAddr() {
                String remoteIp = getHeader("x-forwarded-for");
                if (null != remoteIp) {
                    return remoteIp;
                }
                return super.getRemoteAddr();
            }
        };
        REQUEST.set(wrapper);
        RESPONSE.set(response);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST.get();
    }

    public static HttpServletResponse getResponse() {
        return RESPONSE.get();
    }

}
