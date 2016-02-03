package org.gagauz.shop.web.components.buyer;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.gagauz.shop.database.model.enums.Currency;

public class MainTemplate {
    @SessionState
    @Property
    private Currency currentCurrency;
}
