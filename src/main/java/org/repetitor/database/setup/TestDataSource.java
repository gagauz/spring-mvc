package org.repetitor.database.setup;

import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.repetitor.database.setup.utils.StatementInterceptor;
import org.repetitor.utils.SysEnv;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class TestDataSource extends SimpleDriverDataSource implements DataSource {

    public TestDataSource() {
        for (Entry e : System.getProperties().entrySet()) {
            System.out.format("%1$40s = %2$s\n", e.getKey(), e.getValue());
        }
        setDriverClass(com.mysql.jdbc.Driver.class);
        setUrl(SysEnv.JDBC_URL.toString());
        setUsername(SysEnv.JDBC_USERNAME.toString());
        setPassword(SysEnv.JDBC_PASSWORD.toString());
        Properties props = new Properties();
        props.setProperty("cacheServerConfiguration", "true");
        props.setProperty("useUnicode", "true");
        props.setProperty("characterEncoding", "UTF-8");
        props.setProperty("characterSetResults", "UTF-8");
        props.setProperty("useLocalSessionState", "true");
        props.setProperty("statementInterceptors", StatementInterceptor.class.getName());
        setConnectionProperties(props);
    }
}
