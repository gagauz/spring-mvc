package org.webservice.database.model.enums;

public enum AccessRole {
    ADMIN,
    ORDER_MANAGER,
    USER_MANAGER;

    public static final AccessRole[] EMPTY_ROLES = new AccessRole[0];
}
