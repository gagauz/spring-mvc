package org.gagauz.shop.web.pages.seller.shop;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.pages.seller.SellerShops;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class Products {
    @Component(parameters = {"object=product", "exclude=id,created,updated", "add=parent"})
    private BeanEditForm form;

    @Component(parameters = {"model=parentsModel", "value=product.category", "blankOption=ALWAYS", "validate=required"})
    private Select parent;

    @Component(parameters = {"source=products", "row=row"})
    private Grid grid;

    @Property
    private Shop shop;

    @Property
    private Product row;

    @Property
    private Product product;

    @Inject
    private ProductDao productDao;

    @Inject
    private ProductCategoryDao productCategoryDao;

    @Inject
    private SelectModelFactory selectModelFactory;

    @SessionState
    private Seller seller;

    Object onActivate(final Shop shop0) {
        if (null == shop0 || !shop0.getSeller().equals(seller)) {
            return SellerShops.class;
        }
        shop = shop0;
        return null;
    }

    Object onPassivate() {
        return shop;
    }

    @Cached
    public List<Product> getProducts() {
        return productDao.findByShop(shop);
    }

    public SelectModel getParentsModel() {
        return selectModelFactory.create(productCategoryDao.findByShop(shop), "hierarchyName");
    }

    void onSuccessFromForm() {
        product.setShop(shop);
        productDao.save(product);
    }

}
