package org.gagauz.shop.web.pages.shop;

import java.util.List;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.hibernate.utils.EntityFilter;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.tapestry.web.services.annotation.GetParam;

public class ShopCatalog {

    @GetParam
    @Property
    private Shop shop;

    @GetParam
    @Property
    private Product product;

    @GetParam
    @Property
    private ProductCategory category;

    @Property(write = false)
    private Product zoomProduct;

    @Inject
    private ProductDao productDao;

    private EntityFilter getFilter() {
        EntityFilter filter = new EntityFilter();
        if (null != shop) {
            filter.addAlias("shop", "shop").eq("shop", shop);
        }
        if (null != category) {
            filter.addAlias("category", "category").eq("category", category);
        }
        return filter;
    }

    @Cached
    public List<Product> getProducts() {
        return productDao.findByFilter(getFilter());
    }

    public void setZoomProduct(Product product2) {
        this.zoomProduct = product2;
    }
}
