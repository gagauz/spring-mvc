package org.gagauz.shop.services;

import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class CategoriesImporter {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    public void importCategories(Shop shop, InputStream stream) {

        Map<String, Map<String, ProductCategory>> productCats = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String l;
        try {

            while ((l = reader.readLine()) != null) {
                String[] ids = l.split("\t|/");
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

}
