package org.gagauz.shop.web.pages.seller.shop;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ShopDao;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.services.CategoriesImporter;
import org.gagauz.shop.web.pages.seller.SellerShops;
import org.gagauz.shop.web.services.security.Secured;
import org.gagauz.tapestry.web.services.model.CollectionGridDataSourceRowTypeFix;

import java.util.List;
import java.util.function.Function;

@Secured(AccessRole.SELLER)
public class Categories {
    @Component(parameters = {"object=category", "exclude=id,created,updated", "add=parent"})
    private BeanEditForm form;

    @Component(parameters = {"model=parentsModel", "value=category.parent", "blankOption=ALWAYS"})
    private Select parent;

    @Component(parameters = {"source=getGridDataSource()", "row=row", "add=edit", "exclude=name"})
    private Grid grid;

    @Property
    private ProductCategory category;

    @Property
    private ProductCategory row;

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

    @Inject
    private CategoriesImporter shopCategoriesImporter;

    @SessionState
    private Seller seller;

    Object onActivate(EventContext ctx) {
        if (ctx.getCount() == 0) {
            return SellerShops.class;
        }
        Shop shop0 = ctx.get(Shop.class, 0);
        if (null == shop0 || !shop0.getSeller().equals(seller)) {
            return SellerShops.class;
        }
        shop = shop0;

        if (ctx.getCount() > 1) {
            category = ctx.get(ProductCategory.class, 1);
        }

        return null;
    }

    void onEdit(ProductCategory cat) {
        this.category = cat;
    }

    void onDrop(ProductCategory cat) {
        productCategoryDao.delete(cat);
    }

    Object onPassivate() {
        return new Object[] {shop, category};
    }

    @Cached
    public GridDataSource getGridDataSource() {
        return new CollectionGridDataSourceRowTypeFix<>(getCategories(), ProductCategory.class);
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

    @Cached
    public Function<UploadedFile, Void> getImporter() {
        return new Function<UploadedFile, Void>() {
            @Override
            public Void apply(UploadedFile t) {
                shopCategoriesImporter.importCategories(shop, t.getStream());
                return null;
            }
        };
    }
}
