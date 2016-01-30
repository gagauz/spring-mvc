package org.gagauz.shop.web.components.shop;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.web.pages.shop.ShopCatalog;

@Import(module = "bootstrap/carousel")
public class ProductCard {
    @Parameter
    @Property
    private Product product;

    @Parameter(defaultPrefix = BindingConstants.COMPONENT)
    private Zone zone;

    @Property
    private String image;

    @InjectContainer
    private ShopCatalog catalog;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void onZoomIn(Product product) {
        catalog.setZoomProduct(product);
        ajaxResponseRenderer.addRender(zone).addCallback(new JavaScriptCallback() {
            @Override
            public void run(JavaScriptSupport javascriptSupport) {
                javascriptSupport.require("modal").invoke("showModal").with("zoomIn-modal");
            }
        });
    }
}
