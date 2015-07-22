package org.webservice.database.datasource;

import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.webservice.database.utils.StatementInterceptor;
import org.webservice.utils.SysEnv;

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
