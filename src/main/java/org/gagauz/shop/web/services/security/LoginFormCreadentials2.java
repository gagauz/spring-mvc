package org.gagauz.shop.web.services.security;

import org.gagauz.shop.database.model.AbstractUser;
import org.gagauz.tapestry.security.impl.LoginFormCredentials;

public class LoginFormCreadentials2 extends LoginFormCredentials {

    private Class<AbstractUser> userClass;

    public LoginFormCreadentials2(String username, String password, boolean remember, Class<AbstractUser> userClass) {
        super(username, password, remember);
        this.userClass = userClass;
    }

    public Class<AbstractUser> getUserClass() {
        return userClass;
    }

}
