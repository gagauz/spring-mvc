package org.gagauz.shop.web.components.seller;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class ShopMenu {
    @Parameter(principal = true)
    @Property
    private Shop shop;

    boolean setupRender() {
        return shop != null;
    }

}
