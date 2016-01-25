package org.gagauz.shop.web.pages.seller.shop;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.dao.CurrencyRateDao;
import org.gagauz.shop.database.model.CurrencyRate;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.pages.seller.SellerShops;
import org.gagauz.shop.web.services.security.Secured;

import java.util.List;

@Secured(AccessRole.SELLER)
public class CurrencyRates {
    @Component(parameters = {"object=rate", "exclude=id,created,updated"})
    private BeanEditForm form;

    @Component(parameters = {"source=rates", "row=row"})
    private Grid grid;

    @Property
    private Shop shop;

    @Property
    private CurrencyRate rate;

    @Property
    private CurrencyRate row;

    @Inject
    private CurrencyRateDao currencyRateDao;

    @SessionState
    private Seller seller;

    Object onActivate(final Shop shop0) {
        if (null == shop0 || !shop0.getSeller().equals(seller)) {
            return SellerShops.class;
        }
        shop = shop0;
        return null;
    }

    Object onActivate(final Shop shop0, final CurrencyRate rate0) {
        Object o = onActivate(shop0);
        rate = rate0;
        return o;
    }

    Object onPassivate() {
        return new Object[] {shop, rate};
    }

    @Cached
    public List<CurrencyRate> getRates() {
        return currencyRateDao.findByShop(shop);
    }

    void onSuccessFromForm() {
        rate.setShop(shop);
        currencyRateDao.save(rate);
        rate = null;
    }

}
