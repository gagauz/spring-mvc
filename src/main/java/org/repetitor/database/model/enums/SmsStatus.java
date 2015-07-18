package org.repetitor.database.model.enums;

public enum SmsStatus {
    OPEN,
    SENT_OK,
    UNKNOWN,
    NO_RECIPIENTS,
    NOT_ENOUGH_CREDITS,
    AUTH_FAILED,
    EXCEPTION;
}
