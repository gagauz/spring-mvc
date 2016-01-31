package org.gagauz.shop.web.pages.shop;

import org.apache.tapestry5.annotations.Property;
import org.gagauz.shop.database.model.Product;

public class ShopProduct {

    @Property
    private Product product;

    Object onActivate(Product product) {
        this.product = product;
        if (null == product) {
            return ShopCatalog.class;
        }
        return null;
    }
}
