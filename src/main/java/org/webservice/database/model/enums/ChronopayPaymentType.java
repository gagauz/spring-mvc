package org.webservice.database.model.enums;

public enum ChronopayPaymentType {
    CARD(1),
    WEBMONEY(15),
    YANDEXMONEY(16);

    private int type;

    ChronopayPaymentType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }
}
