package org.gagauz.shop.web.pages.shop;

import java.math.BigDecimal;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.shop.database.model.BasketItem;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.web.services.shop.BasketService;

public class ShopBasket {
    @Inject
    private BasketService basketService;

    @Property
    private BasketItem item;

    public List<BasketItem> getBasketItems() {
        return basketService.getItems();
    }

    void onIncQty(Product product) {
        basketService.addItem(product, 1);
    }

    void onDecQty(Product product) {
        basketService.addItem(product, -1);
    }

    void onRemove(Product product) {
    }

    public BigDecimal getSubTotal() {
        return item.getProduct().getPrice().multiply(new BigDecimal(item.getCount()));
    }
}
