package org.gagauz.shop.web.components.shop;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.ProductAttribute;
import org.gagauz.shop.database.model.ProductGroup;
import org.gagauz.shop.database.model.ProductVariant;
import org.gagauz.shop.web.services.shop.BasketService;

@Import(module = "bootstrap/carousel")
public class ProductDetails {

    @Component
    private Zone zone;

    @Parameter
    @Property
    private Product product;

    @Property
    private Product buyedProduct;

    @Property
    private String image;

    @Property
    private ProductVariant variant;

    @Property
    private Product row;

    @Property
    private ProductGroup group;

    @Property
    private ProductAttribute attribute;

    @Inject
    private BasketService basketService;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    boolean setupRender() {
        return product != null;
    }

    void onBuy(Product product) {
        buyedProduct = product;
        basketService.addItem(product, 1);
        ajaxResponseRenderer.addRender("add-basket-popover", zone.getBody()).addCallback(new JavaScriptCallback() {
            @Override
            public void run(JavaScriptSupport javascriptSupport) {
                javascriptSupport.require("basket-popover").invoke("showPopover").with("#basket-icon", "#add-basket-popover", "#basket-item-count",
                        basketService.getItems().size());

            }
        });
    }

    void onFavorite(Product product) {

    }

    public boolean isCurrent() {
        return product.equals(row);
    }

    public ProductVariant getGroupVariant() {
        for (ProductVariant v : row.getVariants()) {
            if (v.getGroup().equals(group)) {
                return v;
            }
        }
        return null;
    }
}
