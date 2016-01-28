package org.gagauz.shop.web.pages.shop;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.gagauz.hibernate.utils.EntityFilter;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.tapestry.web.services.annotation.GetParam;

import java.util.List;

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

    @Property
    private String image;

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
}
