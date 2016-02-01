package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDao extends ShopEntityDao<Integer, Product> {

    @Override
    public List<Product> findByShop(Shop shop) {
        return createQuery("select p from Product p inner join p.category c where c.shop=:shop")
                .setEntity("shop", shop)
                .list();
    }

}
