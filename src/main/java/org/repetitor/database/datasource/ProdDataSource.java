package org.repetitor.database.datasource;

import java.util.Map.Entry;

import javax.sql.DataSource;

import org.repetitor.utils.SysEnv;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ProdDataSource extends HikariDataSource implements DataSource {

    public ProdDataSource() {
        super(getConfig());
    }

    private static HikariConfig getConfig() {
        for (Entry e : System.getProperties().entrySet()) {
            System.out.format("%1$40s = %2$s\n", e.getKey(), e.getValue());
        }
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

        return config;
    }

}
