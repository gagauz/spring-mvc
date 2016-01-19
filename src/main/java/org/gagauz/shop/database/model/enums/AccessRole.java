package org.gagauz.shop.database.model.enums;

public enum AccessRole {
    BUYER,
    SELLER,
    ADMIN;

    public static final AccessRole[] EMPTY_ROLES = new AccessRole[0];
}
