package org.repetitor.database.setup;

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
        setUrl(System.getProperty("tracker.jdbc-url",
                "jdbc:mysql://localhost:3306/repetitor?autoReconnect=true"));
        setUsername(System.getProperty("tracker.db-username", "b4f"));
        setPassword(System.getProperty("tracker.db-password", "office"));
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
