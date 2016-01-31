package org.gagauz.shop.database.dao;

import java.util.List;

import org.gagauz.shop.database.model.BasketItem;
import org.gagauz.shop.database.model.Buyer;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

@Service
public class BasketItemDao extends AbstractDao<Integer, BasketItem> {
    @SuppressWarnings("unchecked")
    public List<BasketItem> findByBuyer(Buyer buyer) {
        return createCriteria().add(Restrictions.eq("buyer", buyer)).list();
    }
}
