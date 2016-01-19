package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Payment;
import org.gagauz.shop.database.model.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentDao extends AbstractDao<Integer, Payment> {

    public List<Payment> findByRepetitor(Shop shop) {
        return createQuery("select p from Payment p where p.shop=:shop")
                .setEntity("repetitor", shop).list();
    }

}
