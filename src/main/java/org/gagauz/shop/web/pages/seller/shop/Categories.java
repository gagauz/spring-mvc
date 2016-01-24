package org.gagauz.shop.web.pages.seller.shop;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ShopDao;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.web.pages.seller.SellerShops;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class Categories {
    @Component(parameters = {"object=category", "exclude=id,created,updated", "add=parent"})
    private BeanEditForm form;

    @Component(parameters = {"model=parentsModel", "value=category.parent", "blankOption=ALWAYS"})
    private Select parent;

    @Property
    private ProductCategory category;

    @Property
    private Shop shop;

    //    @Property
    //    private ProductCategory category;

    @Inject
    private ProductCategoryDao productCategoryDao;

    @Inject
    private SelectModelFactory selectModelFactory;

    @Inject
    private ShopDao shopDao;

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
    public List<ProductCategory> getCategories() {
        return productCategoryDao.findByShop(shop);
    }

    public SelectModel getParentsModel() {
        return selectModelFactory.create(getCategories(), "hierarchyName");
    }

    void onSuccessFromForm() {
        category.setShop(shop);
        productCategoryDao.save(category);
    }

}
