package org.gagauz.shop.web.pages.seller;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class Settings {
    @Component(parameters = {"object=seller", "exclude=id,created,updated,password", "add=newPassword1,newPassword2"})
    private BeanEditForm sellerForm;

    @Inject
    private SellerDao sellerDao;

    @SessionState
    @Property
    private Seller seller;

    @Component(parameters = {"value=newPassword1"})
    private PasswordField passwordField1;

    @Component(parameters = {"value=newPassword2"})
    private PasswordField passwordField2;

    @Property
    private String newPassword1;

    @Property
    private String newPassword2;

    @Persist("flash")
    @Property
    private boolean success;

    void onSuccessFromSellerForm() {
        if (null != newPassword1) {
            if (newPassword1.equals(newPassword2)) {
                seller.createPassword(newPassword1);
            } else {
                sellerForm.recordError(passwordField2, "Пароли не совпадают");
            }
        }
        if (sellerForm.isValid()) {
            sellerDao.merge(seller);
            success = true;
        }
    }
}
