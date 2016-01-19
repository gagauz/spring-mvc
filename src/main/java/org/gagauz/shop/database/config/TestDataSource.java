package org.gagauz.shop.database.config;

import org.gagauz.hibernate.config.HibernateSysEnv;
import org.gagauz.hibernate.utils.StatementInterceptor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

import java.util.Map.Entry;
import java.util.Properties;

public class TestDataSource extends SimpleDriverDataSource implements DataSource {

    public TestDataSource() {
        for (Entry e : System.getProperties().entrySet()) {
            System.out.format("%1$40s = %2$s\n", e.getKey(), e.getValue());
        }
        setDriverClass(com.mysql.jdbc.Driver.class);
        setUrl(HibernateSysEnv.JDBC_URL.toString());
        setUsername(HibernateSysEnv.JDBC_USERNAME.toString());
        setPassword(HibernateSysEnv.JDBC_PASSWORD.toString());
        Properties props = new Properties();
        props.setProperty("cacheServerConfiguration", "true");
        props.setProperty("useUnicode", "true");
        props.setProperty("characterEncoding", "UTF-8");
        props.setProperty("characterSetResults", "UTF-8");
        props.setProperty("useLocalSessionState", "true");
        props.setProperty("statementInterceptors", StatementInterceptor.class.getName());
        props.setProperty("includeThreadDumpInDeadlockExceptions", "true");
        props.setProperty("logSlowQueries", "true");
        props.setProperty("includeInnodbStatusInDeadlockExceptions", "true");
        props.setProperty("logger", "com.mysql.jdbc.log.Slf4JLogger");
        props.setProperty("dumpQueriesOnException", "true");
        setConnectionProperties(props);
    }
}
