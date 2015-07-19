package org.repetitor.setup;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

import java.util.Properties;

import javax.sql.DataSource;

import org.repetitor.database.setup.ProdDataSource;
import org.repetitor.database.setup.SessionFactoryBean;
import org.repetitor.database.setup.TestDataSource;
import org.repetitor.utils.SysEnv;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {
        "org.repetitor.database.dao",
        "org.repetitor.services" })
public class Setup {

    @Bean(autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        return ("true".equals(SysEnv.PRODUCTION_MODE.toString()))
                ? new ProdDataSource()
                : new TestDataSource();

    }

    @Bean(autowire = Autowire.BY_NAME)
    public LocalSessionFactoryBean sessionFactory() {
        Properties properties = new Properties();
        if ("true".equals(SysEnv.PRODUCTION_MODE.toString())) {
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
