package org.gagauz.shop.web.components.buyer;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.gagauz.shop.database.model.enums.Currency;
import org.gagauz.shop.web.services.shop.BasketService;

@Import(module = "bootstrap/dropdown")
public class MainMenu {
    @Property
    private Currency currency;

    @SessionState
    @Property
    private Currency currentCurrency;

    @Inject
    private Cookies cookies;

    @Inject
    private BasketService basketService;

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public int getItemCount() {
        return basketService.getItems().size();
    }

    public boolean isCurrent() {
        return currentCurrency.equals(currency);
    }

    void onSetCurrency(Currency currency) {
        currentCurrency = currency;
        cookies.getBuilder("currency", currency.name()).setMaxAge(Integer.MAX_VALUE).setPath("/").write();
    }
}
