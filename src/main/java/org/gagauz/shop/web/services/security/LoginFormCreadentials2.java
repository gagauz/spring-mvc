package org.gagauz.shop.web.services.security;

import org.gagauz.tapestry.security.impl.LoginFormCredentials;

public class LoginFormCreadentials2 extends LoginFormCredentials {

    private Class userClass;

    public LoginFormCreadentials2(String username, String password, boolean remember, Class userClass) {
        super(username, password, remember);
        this.userClass = userClass;
    }

    public Class getUserClass() {
        return userClass;
    }

}
