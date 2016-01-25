package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.CurrencyRate;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyRateDao extends AbstractDao<Integer, CurrencyRate> {
    public List<CurrencyRate> findByShop(Shop shop) {
        return createQuery("select c from CurrencyRate c where c.shop=:shop")
                .setEntity("shop", shop)
                .list();
    }
}
