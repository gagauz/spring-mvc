package org.gagauz.shop.web.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.dao.BuyerDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.web.services.security.LoginFormCreadentials2;
import org.gagauz.tapestry.security.AuthenticationService;
import org.gagauz.tapestry.security.UserSet;

@Import(module = "bootstrap/tab")
public class Registration {

    @Property
    private Seller seller;

    @Property
    private Buyer buyer;

    @Inject
    private SellerDao sellerDao;

    @Inject
    private BuyerDao buyerDao;

    @Inject
    private AuthenticationService authenticationService;

    @SessionState(create = false)
    private UserSet users;

    Object onActivate() {
        if (null != users && !users.isEmpty()) {
            return Index.class;
        }
        return null;
    }

    void onSuccessFromSellerForm() {
        String password = seller.getPassword();
        seller.createPassword(password);
        sellerDao.save(seller);
        authenticationService.login(new LoginFormCreadentials2(seller.getEmail(), password, true, Seller.class));
    }

    void onSuccessFromBuyerForm() {
        String password = buyer.getPassword();
        buyer.createPassword(password);
        buyerDao.save(buyer);
        authenticationService.login(new LoginFormCreadentials2(buyer.getEmail(), password, true, Buyer.class));
    }
}
