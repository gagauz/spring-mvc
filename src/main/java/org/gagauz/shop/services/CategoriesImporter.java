package org.gagauz.shop.services;

import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoriesImporter extends AbstractCsvImporter {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    private Map<String, ProductCategory> idToCategory;
    private Map<String, ProductCategory> nameToCategory;
    private Shop shop;

    public synchronized void importCategories(Shop shop, InputStream stream) {
        this.shop = shop;
        importCsvFile(stream, "\t");
    }

    private ProductCategory createCategory(String name, String shopId, ProductCategory parent, Shop shop) {
        ProductCategory pc = idToCategory.get(shopId);

        if (null == pc) {
            pc = new ProductCategory();
            pc.setExternalId(shopId);
            pc.setShop(shop);
            idToCategory.put(shopId, pc);
        }

        pc.setName(name);
        if (null != parent) {
            pc.setParent(parent);
        }
        productCategoryDao.saveNoCommit(pc);
        return pc;
    }

    @Override
    void init() {
        idToCategory = new HashMap<>();
        nameToCategory = new HashMap<>();
        if (null != shop) {
            for (ProductCategory c : productCategoryDao.findByShop(shop)) {
                idToCategory.put(c.getExternalId(), c);
                nameToCategory.put(c.getHierarchyName(), c);
            }
        }

    }

    private ProductCategory createCategoryFromName(String id, String name) {
        ProductCategory category = null;
        if (null != id) {
            category = idToCategory.get(id);
        } else {
            category = nameToCategory.get(name);
        }
        int i = name.lastIndexOf('/');
        if (null == category) {
            category = new ProductCategory();
            if (i > 0) {
                category.setName(name.substring(i + 1));
            } else {
                category.setName(name);
            }
            category.setExternalId(id);
            category.setShop(shop);
        }
        ProductCategory parent = null;
        if (i > 0) {
            String parentName = name.substring(0, i);
            parent = createCategoryFromName(null, parentName);
        }
        category.setParent(parent);
        productCategoryDao.saveNoCommit(category);
        nameToCategory.put(name, category);
        return category;
    }

    @Override
    void process(String[] ids) {
        if (ids.length < 2) {
            throw new IllegalStateException("Неправильный формат файла, кол-во колонок меньше 2-х.");
        }
        if (StringUtils.isEmpty(ids[1])) {
            throw new IllegalStateException("Пустое имя для категории :" + ids[1]);
        }
        createCategoryFromName(ids[0], ids[1]);
    }

    @Override
    void commit() {
        productCategoryDao.flush();
        idToCategory = null;
        nameToCategory = null;
        shop = null;
    }

}
