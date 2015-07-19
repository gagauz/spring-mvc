package org.repetitor.setup;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.repetitor.database.model.enums.MessageType;
import org.repetitor.database.setup.ProdDataSource;
import org.repetitor.database.setup.TestDataSource;
import org.repetitor.database.setup.utils.SessionFactoryBean;
import org.repetitor.services.template.FreemarkerService;
import org.repetitor.utils.SysEnv;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

@Configuration
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {
        "org.repetitor.database.dao",
        "org.repetitor.services" })
public class Setup {

    @Bean(autowire = Autowire.BY_NAME)
    public DataSource dataSource() {
        return (SysEnv.PRODUCTION_MODE.get().toBool())
                ? new ProdDataSource()
                : new TestDataSource();

    }

    @Bean(autowire = Autowire.BY_NAME)
    @Autowired
    public ViewResolver viewResolver(FreemarkerService freemarkerService) {
        return new ViewResolver() {
            @Override
            public View resolveViewName(String viewName, Locale locale) throws Exception {
                return new View() {

                    @Override
                    public String getContentType() {
                        return "text/html; charset=utf-8";
                    }

                    @Override
                    public void render(Map<String, ?> model, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
                        freemarkerService.createEmailContent(MessageType.CONTACT_SENT, model);
                    }

                };
            }
        };
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
