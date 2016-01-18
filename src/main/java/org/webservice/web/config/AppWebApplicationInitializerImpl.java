package org.webservice.web.config;

import org.gagauz.tapestry.web.services.AppWebApplicationInitializer;
import org.webservice.config.TestConfig;
import org.webservice.database.config.HibernateConfigImpl;
import org.webservice.utils.SysEnv;

import java.util.ArrayList;
import java.util.List;

public class AppWebApplicationInitializerImpl extends AppWebApplicationInitializer {

    @Override
    protected Class<?>[] getConfigClasses() {
        List<Class<?>> list = new ArrayList<>();
        list.add(HibernateConfigImpl.class);
        if (!SysEnv.PRODUCTION_MODE.get().toBool()) {
            list.add(TestConfig.class);
        }
        return list.toArray(new Class[]);
    }

    @Override
    protected String getTapestryAppPackage() {
        return "org.webservice.web";
    }

    @Override
    protected Boolean getUseExternalSpringContext() {
        return "true";
    }

}
