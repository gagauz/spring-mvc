package org.gagauz.shop.web.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.model.Admin;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.web.services.security.SellerCredentials;
import org.gagauz.tapestry.security.AuthenticationService;
import org.gagauz.tapestry.security.UserSet;
import org.gagauz.tapestry.security.api.User;

public class Login {

    @Component(parameters = {"object=login"})
    private BeanEditForm loginForm;

    @Property
    private SellerCredentials login;

    @Inject
    private AuthenticationService authenticationService;

    @SessionState(create = false)
    private UserSet users;

    Object onActivate() {
        if (null != users && !users.isEmpty()) {
            for (User user : users) {
                if (user instanceof Seller) {
                    return org.gagauz.shop.web.pages.seller.Index.class;
                } else if (user instanceof Buyer) {
                    return org.gagauz.shop.web.pages.buyer.Index.class;
                } else if (user instanceof Admin) {
                    return Index.class;
                }
            }
        }
        return null;
    }

    void onSubmitFromLoginForm() {
        Seller seller = authenticationService.login(login);
        if (null == seller) {
            loginForm.recordError("Неправильный e-mail или пароль!");
        }

    }
}
