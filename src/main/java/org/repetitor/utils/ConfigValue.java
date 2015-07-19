package org.repetitor.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ConfigValue {
    private final String value;

    ConfigValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean toBool() {
        return Boolean.valueOf(value);
    }

    public int toInt() {
        return NumberUtils.toInt(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        return this == obj || toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return null == value ? 0 : value.hashCode();
    }
}
