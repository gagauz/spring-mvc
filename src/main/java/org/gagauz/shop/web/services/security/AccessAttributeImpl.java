package org.gagauz.shop.web.services.security;

import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.tapestry.security.api.AccessAttribute;

public class AccessAttributeImpl implements AccessAttribute {

    private final AccessRole[] roles;

    public AccessAttributeImpl(AccessRole[] roles) {
        this.roles = roles;
    }

    public AccessRole[] getRoles() {
        return roles;
    }
}
