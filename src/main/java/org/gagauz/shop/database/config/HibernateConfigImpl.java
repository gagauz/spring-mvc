package org.gagauz.shop.database.config;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

import java.util.Properties;

import javax.sql.DataSource;

import org.gagauz.hibernate.config.HibernateConfig;
import org.gagauz.shop.database.dao.AbstractDao;
import org.gagauz.shop.services.ConfigService;
import org.gagauz.shop.utils.SysEnv;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {AbstractDao.class, ConfigService.class})
public class HibernateConfigImpl extends HibernateConfig {

    @Override
    @Bean(autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        if (SysEnv.PRODUCTION_MODE.get().toBool()) {
            return new ProdDataSource();
        }
        return new TestDataSource();
    }

    @Override
    @Bean(autowire = Autowire.BY_NAME)
    public SessionFactoryBean sessionFactory() {
        Properties properties = new Properties();
        if (SysEnv.PRODUCTION_MODE.get().toBool()) {
            properties.put(HBM2DDL_AUTO, "false");
        } else {
            properties.put(HBM2DDL_AUTO, "create");
        }
        return new SessionFactoryBean(properties);
    }

}
