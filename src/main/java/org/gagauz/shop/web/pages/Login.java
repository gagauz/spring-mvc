package org.gagauz.shop.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.gagauz.shop.web.services.security.SellerCredentials;

public class Login {
    @Property
    private SellerCredentials login;
}
