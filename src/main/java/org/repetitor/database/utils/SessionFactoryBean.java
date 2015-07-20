package org.repetitor.database.utils;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;
import org.repetitor.database.model.Manager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

public class SessionFactoryBean extends LocalSessionFactoryBean {
    public SessionFactoryBean(Properties properties) {
        setPackagesToScan(Manager.class.getPackage().getName());
        setAnnotatedPackages(new String[] {Manager.class.getPackage().getName()});
        properties.put(DIALECT, MySQL5InnoDBDialect2.class.getName());
        properties.put(SHOW_SQL, false);
        properties.put(FORMAT_SQL, false);
        properties.put(USE_SQL_COMMENTS, false);
        properties.put(STATEMENT_BATCH_SIZE, 50);
        properties.put(STATEMENT_BATCH_SIZE, 50);
        properties.put("hibernate.validator.autoregister_listeners", "create");
        properties.put(USE_REFLECTION_OPTIMIZER, true);
        properties.put(DEFAULT_BATCH_FETCH_SIZE, 50);
        properties.put("current_session_context_class", "thread");
        properties.put("connection.characterEncoding", "utf-8");
        properties.put("hibernate.connection.charset", "utf8");
        properties.put("hibernate.connection.characterEncoding", "utf-8");
        properties.put("hibernate.connection.useUnicode", "true");
        setHibernateProperties(properties);

    }

    @Override
    public void afterPropertiesSet() throws IOException {
        super.afterPropertiesSet();
        getConfiguration().addSqlFunction("bit_or",
                new StandardSQLFunction("bit_or", new StringType()));
    }
}
