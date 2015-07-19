package org.repetitor.utils;

public enum SysEnv {
    JDBC_USERNAME("b4f"),
    JDBC_PASSWORD("office"),
    JDBC_URL("jdbc:mysql://localhost:3306/repetitor?autoReconnect=true"),
    MAIL_TEMPLATE_DIR("/var/tmp"),
    PRODUCTION_MODE("false");

    private String value;
    private final String defaultValue;

    SysEnv(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        if (null == value) {
            value = System.getenv(name());
            if (null == value) {
                if (null == defaultValue) {
                    throw new IllegalStateException("No system variable with name " + name()
                            + " present!");
                }
                value = defaultValue;
            }
        }
        return value;
    }
}
