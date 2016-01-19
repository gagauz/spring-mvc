package org.gagauz.shop.web.config;

import org.gagauz.shop.database.config.HibernateConfigImpl;
import org.gagauz.shop.utils.SysEnv;
import org.gagauz.tapestry.web.services.AppWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.webservice.config.TestConfig;

import java.util.ArrayList;
import java.util.List;

public class AppWebApplicationInitializerImpl extends AppWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    protected Class<?>[] getConfigClasses() {
        List<Class<?>> list = new ArrayList<>();
        list.add(HibernateConfigImpl.class);
        if (!SysEnv.PRODUCTION_MODE.get().toBool()) {
            list.add(TestConfig.class);
        }
        return list.toArray(new Class[list.size()]);
    }

    @Override
    protected String getTapestryAppPackage() {
        return "org.gagauz.shop.web";
    }

    @Override
    protected Boolean getUseExternalSpringContext() {
        return true;
    }

}
