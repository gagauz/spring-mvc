package org.gagauz.shop.services;

import org.apache.commons.lang3.math.NumberUtils;
import org.gagauz.shop.database.dao.ManufacturerDao;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.*;
import org.gagauz.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductsImporter extends AbstractCsvImporter {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;

    private Shop shop;

    private Map<String, ProductCategory> nameToCategory;
    private Map<String, ProductCategory> idToCategory;
    private Map<String, Product> idToProduct;
    private Map<String, Manufacturer> nameToManufacturer;

    public void importProducts(Shop shop, InputStream stream) {

        Map<String, Map<String, ProductCategory>> productCats = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String l;
        try {

            while ((l = reader.readLine()) != null) {
                String[] ids = l.split("\t|/");
                if (ids.length < 2) {
                    throw new IllegalStateException("Неправильный формат файла");
                }

                Map<String, ProductCategory> map = productCats.get(ids[1]);
                if (null == map) {
                    map = new HashMap<>();
                    productCats.put(ids[1], map);
                }
                for (int i = 1; i < ids.length; i++) {

                    ProductCategory pc = map.get(ids[i]);
                    if (null == pc) {
                        ProductCategory pa = null;
                        if (i > 1) {
                            pa = map.get(ids[i - 1]);
                        }
                        pc = createCategory(ids[i], ids[0], pa, shop);
                        map.put(ids[i], pc);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productCategoryDao.flush();
    }

    private ProductCategory createCategory(String name, String shopId, ProductCategory parent, Shop shop) {
        ProductCategory pc = new ProductCategory();
        pc.setName(name);
        pc.setExternalId(shopId);
        pc.setShop(shop);
        if (null != parent) {
            pc.setParent(parent);
        }
        productCategoryDao.saveNoCommit(pc);
        //        productCategoryDao.save(pc);

        return pc;
    }

    @Override
    void init() {
        idToProduct = new HashMap<>();
        nameToCategory = new HashMap<>();
        idToCategory = new HashMap<>();
        nameToManufacturer = new HashMap<>();
        for (ProductCategory c : productCategoryDao.findAllByShop(shop)) {
            nameToCategory.put(c.getHierarchyName(), c);
            idToCategory.put(c.getExternalId(), c);
        }

        for (Product p : productDao.findByShop(shop)) {
            idToProduct.put(p.getArticle(), p);
        }

        for (Manufacturer m : manufacturerDao.findByShop(shop)) {
            nameToManufacturer.put(m.getName(), m);
        }
    }

    @Override
    void process(String[] ids) {
        Product p = idToProduct.get(ids[0]);
        if (null == p) {
            p = new Product();
            p.setArticle(ids[0]);
            idToProduct.put(p.getArticle(), p);
        }

        p.setName(ids[1]);
        p.setCategory(parseCategory(ids[2]));
        p.setManufacturer(parseManufacturer(ids[3]));
        p.setPrice(new BigDecimal(ids[4]));
        p.setDiscount(NumberUtils.toInt(ids[5]));
        p.setDescription(ids[6]);
        p.setImages(ids[7]);
        p.setAttributes(parseAttributes(ids[8]));

    }

    @Override
    void commit() {
        productDao.save(idToProduct.values());
    }

    private ProductCategory parseCategory(String string) {
        ProductCategory pc = idToCategory.get(string);
        if (null == pc) {
            pc = nameToCategory.get(string);
        }
        if (null == pc) {
            throw new RuntimeException("Неизвестный идентификатор категории :" + string);
        }
        return pc;
    }

    private Manufacturer parseManufacturer(String string) {
        if (!StringUtils.isEmpty(string)) {
            Manufacturer pc = nameToManufacturer.get(string);
            if (null == pc) {
                throw new RuntimeException("Неизвестный идентификатор производителя :" + string);
            }
            return pc;
        }
        return null;
    }

    private List<ProductAttribute> parseAttributes(String string) {
        return null;
    }

}
