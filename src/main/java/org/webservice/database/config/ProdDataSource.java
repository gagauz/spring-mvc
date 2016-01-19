package org.webservice.database.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.gagauz.hibernate.config.HibernateSysEnv;

public class ProdDataSource extends HikariDataSource {

    public ProdDataSource() {
        super(configure());
    }

    private static HikariConfig configure() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(HibernateSysEnv.JDBC_URL.toString());
        config.setUsername(HibernateSysEnv.JDBC_USERNAME.toString());
        config.setPassword(HibernateSysEnv.JDBC_PASSWORD.toString());
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

        //        props.setProperty("statementInterceptors", StatementInterceptor.class.getName());
        //        props.setProperty("includeThreadDumpInDeadlockExceptions", "true");
        //        props.setProperty("logSlowQueries", "true");
        //        props.setProperty("includeInnodbStatusInDeadlockExceptions", "true");
        //        props.setProperty("logger", "com.mysql.jdbc.log.Slf4JLogger");
        //        props.setProperty("dumpQueriesOnException", "true");
        return config;
    }

}
