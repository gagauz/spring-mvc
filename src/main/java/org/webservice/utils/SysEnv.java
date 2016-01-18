package org.webservice.utils;

public enum SysEnv implements Config {
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
