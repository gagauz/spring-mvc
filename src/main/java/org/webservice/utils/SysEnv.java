package org.webservice.utils;

public enum SysEnv implements Config {
    JDBC_USERNAME("b4f"),
    JDBC_PASSWORD("office"),
    JDBC_URL("jdbc:mysql://localhost:3306/repetitor?autoReconnect=true"),
    MAIL_TEMPLATE_DIR("/var/tmp"),
    PAGE_TEMPLATE_DIR("/var/tmp"),
    PRODUCTION_MODE("false");

    private ConfigValue value;

    SysEnv(String defaultValue) {
        String string = System.getenv(name());
        if (null == string && null != defaultValue) {
            this.value = new ConfigValue(defaultValue);
        } else if (null != string) {
            this.value = new ConfigValue(string);
        } else {
            throw new IllegalStateException("No system variable with name " + name()
                    + " present!");
        }

    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public ConfigValue get() {
        return value;
    }
}
