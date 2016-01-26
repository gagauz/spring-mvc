package org.gagauz.shop.services;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesImporter extends AbstractCsvImporter {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    private Map<String, ProductCategory> idToCategory;
    private Map<String, Map<String, ProductCategory>> productCats;
    private Shop shop;

    public synchronized void importCategories(Shop shop, InputStream stream) {
        this.shop = shop;
        importFile(stream, "\t|/");
    }

    private ProductCategory createCategory(String name, String shopId, ProductCategory parent, Shop shop) {
        ProductCategory pc = idToCategory.get(shopId);
        if (null == pc) {
            pc = new ProductCategory();
            pc.setExternalId(shopId);
            pc.setShop(shop);
            productCategoryDao.saveNoCommit(pc);
            idToCategory.put(shopId, pc);
        }
        pc.setName(name);
        if (null != parent) {
            pc.setParent(parent);
        }

        return pc;
    }

    @Override
    void init() {
        idToCategory = new HashMap<>();
        productCats = new HashMap<>();
        if (null != shop) {
            for (ProductCategory c : productCategoryDao.findByShop(shop)) {
                idToCategory.put(c.getExternalId(), c);
            }
        }

    }

    @Override
    void process(String[] ids) {
        if (ids.length < 2) {
            throw new IllegalStateException("Неправильный формат файла, кол-во колонок меньше 2-х.");
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

    @Override
    void commit() {
        productCategoryDao.save(idToCategory.values());
        idToCategory = null;
        productCats = null;
        shop = null;
    }

}
