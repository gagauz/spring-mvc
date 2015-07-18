package org.repetitor.setup;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.repetitor.database.setup.TutorFinderSessionFactoryBean;
import org.repetitor.utils.SysEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

@Configuration
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"org.repetitor.database.dao", "org.repetitor.database.scenarios", "org.repetitor.services"})
public class ProductionConfiguration extends LocalConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ProductionConfiguration.class);

    @Override
    @Bean(autowire = Autowire.BY_NAME)
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(SysEnv.JDBC_URL.toString());
        config.setUsername(SysEnv.JDBC_USERNAME.toString());
        config.setPassword(SysEnv.JDBC_PASSWORD.toString());
        config.setAutoCommit(false);
        config.setConnectionTimeout(3000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(3000);
        config.setDriverClassName(com.mysql.jdbc.Driver.class.getName());

        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("characterEncoding", "UTF-8");
        config.addDataSourceProperty("characterSetResults", "UTF-8");
        config.addDataSourceProperty("useLocalSessionState", "true");
        //        config.addDataSourceProperty("statementInterceptors", StatementInterceptor.class.getName());

        HikariDataSource hikariDataSource = new HikariDataSource(config);

        //        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy(c3p0);
        //        dataSource.setDefaultAutoCommit(false);
        //        return dataSource;
        return hikariDataSource;
    }

    @Override
    @Bean(autowire = Autowire.BY_NAME)
    public LocalSessionFactoryBean sessionFactory() {
        Properties properties = new Properties();
        properties.put(HBM2DDL_AUTO, "false");
        return new TutorFinderSessionFactoryBean(properties);
    }

}
