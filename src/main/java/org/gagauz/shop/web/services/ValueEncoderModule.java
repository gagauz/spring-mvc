package org.gagauz.shop.web.services;

import java.util.Map.Entry;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.gagauz.hibernate.dao.AbstractDao;
import org.gagauz.tapestry.web.services.CommonEntityValueEncoderFactory;

public class ValueEncoderModule {

    public static void contributeValueEncoderSource(MappedConfiguration<Class<?>, ValueEncoderFactory<?>> configuration) {
        for (Entry<Class, AbstractDao> e : AbstractDao.DAO_MAP.entrySet()) {
            configuration.add(e.getKey(), new CommonEntityValueEncoderFactory(e.getValue()));
        }
    }
}
