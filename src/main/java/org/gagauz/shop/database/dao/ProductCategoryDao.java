package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryDao extends ShopEntityDao<Integer, ProductCategory> {

    public List<ProductCategory> findAllByShop(Shop shop) {
        return createQuery("select c from ProductCategory c where c.shop=:shop")
                .setEntity("shop", shop)
                .list();
    }

}
