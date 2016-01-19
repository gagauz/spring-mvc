package org.gagauz.shop.web.services.security;

import org.gagauz.shop.database.model.enums.AccessRole;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Secured {
    AccessRole[] value();
}
