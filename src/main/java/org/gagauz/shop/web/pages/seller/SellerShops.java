package org.gagauz.shop.web.pages.seller;

import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.dao.ShopDao;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class SellerShops {
    @Component(parameters = {"object=shop", "exclude=id,created,updated"})
    private BeanEditForm shopForm;

    @Component(parameters = {"source=shops"})
    private Grid shopGrid;

    @Property
    private Shop shop;

    @Property
    private Shop row;

    @Inject
    private ShopDao shopDao;

    @SessionState
    private Seller seller;

    void onEdit(Shop shop0) {
        shop = shop0;
    }

    void onActivate(Shop shop0) {
        shop = shop0;
    }

    void onDrop(Shop shop0) {
        shopDao.delete(shop0);
        shop = null;
    }

    Object onPassivate() {
        return null == shop ? null : shop.getId();
    }

    void onSuccessFromShopForm() {
        shopDao.save(shop);
    }

    public List<Shop> getShops() {
        return shopDao.findBySeller(seller);
    }
}
