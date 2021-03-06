package org.webservice.database.config;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.webservice.database.dao.AbstractDao;
import org.webservice.utils.SysEnv;

import javax.sql.DataSource;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

@Configuration
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {AbstractDao.class})
public class DatabaseConfig {

    @Bean(autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        return new TestDataSource();
    }

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

    @Bean(autowire = Autowire.BY_NAME)
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager tm = new HibernateTransactionManager();
        tm.setNestedTransactionAllowed(true);
        return tm;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public TransactionInterceptor transactionInterceptor() {
        return new TransactionInterceptor();
    }

    @Bean
    public AnnotationTransactionAttributeSource transactionAttributeSource() {
        return new AnnotationTransactionAttributeSource();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public TransactionAttributeSourceAdvisor transactionAttributeSourceAdvisor() {
        return new TransactionAttributeSourceAdvisor();
    }
}
