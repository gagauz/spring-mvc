package org.gagauz.shop.web.services.security;

import org.gagauz.tapestry.security.api.Credentials;

public abstract class CredentialsImpl implements Credentials {
    private String username;
    private String password;
    private boolean remember;

    public CredentialsImpl() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

}
