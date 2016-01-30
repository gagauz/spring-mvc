package org.gagauz.shop.web.components.shop;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.gagauz.shop.database.model.Product;

public class ProductDetails {
    @Parameter
    @Property
    private Product product;

    @Property
    private String image;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    boolean setupRender() {
        return product != null;
    }

    void onBuy(Product product) {
        ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
            @Override
            public void run(JavaScriptSupport javascriptSupport) {
                javascriptSupport.require("popover").invoke("showPopover").with("#add-basket-popover");
            }
        });
    }

    void onFavorite(Product product) {

    }

}
