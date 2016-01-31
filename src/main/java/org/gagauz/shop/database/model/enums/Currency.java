package org.gagauz.shop.database.model.enums;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public enum Currency {
    RUR("ru"),
    BYR("by"),
    KZT("kz"),
    UAH("ua"),
    MDL("md"),
    USD("en-us"),
    EUR("de", "au", "fr", "it", "bg", "ro"),
    GBP("en-gb");

    private Set<String> locales;

    Currency(String... locales) {
        add(locales);
    }

    private void add(String[] locales) {
        this.locales = new HashSet<>(Arrays.asList(locales));
    }

    public static Currency findByCountry(String locale) {
        for (Currency c : values()) {
            if (c.locales.contains(locale.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    public static Collection<String> getTags() {
        Set<String> tags = new HashSet<>();
        for (Currency c : values()) {
            tags.addAll(c.locales);
        }
        return tags;
    }
}
