package org.gagauz.shop.database.dao;

import java.util.List;

import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryDao extends AbstractDao<Integer, ProductCategory> {

    public List<ProductCategory> findByShop(Shop shop) {
        return createQuery("select distinct c from ProductCategory c left join fetch c.children cs where c.shop=:shop")
                .setEntity("shop", shop)
                .list();
    }

}
