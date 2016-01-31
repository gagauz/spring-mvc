package org.gagauz.shop.web.pages.seller.shop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

import org.apache.tapestry5.EventContext;
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
import org.apache.tapestry5.upload.services.UploadedFile;
import org.gagauz.shop.database.dao.ManufacturerDao;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.AccessRole;
import org.gagauz.shop.services.ProductsImporter;
import org.gagauz.shop.web.pages.seller.SellerShops;
import org.gagauz.shop.web.services.security.Secured;

@Secured(AccessRole.SELLER)
public class Products {
    @Component(parameters = {"object=product", "exclude=id,created,updated", "add=parent,manufacturer"})
    private BeanEditForm form;

    @Component(parameters = {"model=parentsModel", "value=product.category", "blankOption=ALWAYS", "validate=required"})
    private Select parent;

    @Component(parameters = {"model=manufacturerModel", "value=product.manufacturer", "blankOption=ALWAYS"})
    private Select manufacturer;

    @Component(parameters = {"source=products", "row=row", "add=category,edit"})
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
    private ManufacturerDao manufacturerDao;

    @Inject
    private SelectModelFactory selectModelFactory;

    @Inject
    private ProductsImporter productsImporter;

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
            product = ctx.get(Product.class, 1);
        }

        return null;
    }

    Object onPassivate() {
        return new Object[] {shop, product};
    }

    void onEdit(Product product) {
        this.product = product;
    }

    void onDrop(Product product) {
        productDao.delete(product);
    }

    @Cached
    public List<Product> getProducts() {
        return productDao.findByShop(shop);
    }

    public SelectModel getParentsModel() {
        return selectModelFactory.create(productCategoryDao.findByShop(shop), "hierarchyName");
    }

    public SelectModel getManufacturerModel() {
        return selectModelFactory.create(manufacturerDao.findByShop(shop), "name");
    }

    void onSuccessFromForm() {
        product.setShop(shop);
        productDao.save(product);
    }

    @Cached
    public Function<UploadedFile, Void> getImporter() {
        return new Function<UploadedFile, Void>() {
            @Override
            public Void apply(UploadedFile t) {
                Path file;
                try {
                    file = Files.createTempFile("tmp_", t.getFilePath());
                    File f = file.toFile();
                    t.write(f);
                    productsImporter.importProducts(shop, f);
                    f.delete();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

}
