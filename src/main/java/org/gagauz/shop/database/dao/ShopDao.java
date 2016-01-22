package org.gagauz.shop.database.dao;

import java.util.List;

import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

@Service
public class ShopDao extends AbstractDao<Integer, Shop> {

    public List<Shop> findBySeller(Seller seller) {
        return createQuery("select a from Shop a where a.seller=:seller").setEntity("seller", seller).list();
    }

}
