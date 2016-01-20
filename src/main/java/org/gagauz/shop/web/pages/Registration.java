package org.gagauz.shop.web.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.dao.BuyerDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.web.services.security.BuyerCredentials;
import org.gagauz.shop.web.services.security.SellerCredentials;
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
        SellerCredentials c = new SellerCredentials();
        c.setUsername(seller.getEmail());
        c.setPassword(password);
        c.setRemember(true);
        authenticationService.login(c);
    }

    void onSuccessFromBuyerForm() {
        String password = buyer.getPassword();
        buyer.createPassword(password);
        buyerDao.save(buyer);
        BuyerCredentials c = new BuyerCredentials();
        c.setUsername(buyer.getEmail());
        c.setPassword(password);
        c.setRemember(true);
        authenticationService.login(c);
    }
}
