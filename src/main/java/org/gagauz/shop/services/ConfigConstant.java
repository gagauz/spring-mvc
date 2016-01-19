package org.gagauz.shop.services;

import org.gagauz.shop.database.model.enums.ConfigType;

import static org.gagauz.shop.database.model.enums.ConfigType.*;

public enum ConfigConstant {
    SMS_URL(SMS),
    SMS_USER(SMS),
    SMS_PASS(SMS),
    SMS_SUBJECT(SMS),
    MAIL_SMTP_HOST(MAIL),
    MAIL_SMTP_PORT(MAIL),
    MAIL_FROM(MAIL),
    MAIL_REPLY_TO(MAIL),
    STATIC_PHOTO_PATH(PATH),
    CHRONOPAY_CLIENT_ID(CHRONOPAY),
    CHRONOPAY_SECRET(CHRONOPAY),
    CHRONOPAY_TEST_MODE(CHRONOPAY);

    private ConfigType type;

    ConfigConstant(ConfigType type) {
        this.type = type;
    }

    public ConfigType type() {
        return type;
    }

    public String toPropKey() {
        return name().toLowerCase().replace('_', '.');
    }
}
