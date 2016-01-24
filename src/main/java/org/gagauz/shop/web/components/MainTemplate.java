package org.gagauz.shop.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.gagauz.shop.database.model.Shop;

public class MainTemplate {
    @Parameter(autoconnect = true)
    @Property
    private Shop shop;
}
