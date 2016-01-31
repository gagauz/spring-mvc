package org.gagauz.shop.web.components.shop;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.gagauz.shop.database.model.Product;

public class BasketPopover {
    @Parameter
    @Property
    private Product product;

    @Property
    private String image;

    boolean setupRender() {
        return product != null;
    }
}
