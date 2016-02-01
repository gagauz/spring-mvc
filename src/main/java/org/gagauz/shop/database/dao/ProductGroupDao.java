package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.ProductGroup;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupDao extends ShopEntityDao<Integer, ProductGroup> {

    //    @Override
    //    public List<ProductGroup> findByShop(Shop shop) {
    //        return createQuery("select g from ProductGroup g inner join g.products p where g.shop=:shop")
    //                .setEntity("shop", shop)
    //                .list();
    //    }

}
