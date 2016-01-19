package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Buyer;
import org.springframework.stereotype.Service;

@Service
public class BuyerDao extends AbstractDao<Integer, Buyer> {
    public Buyer findByEmail(String username) {
        return (Buyer) createQuery("select a from Buyer a where email=:email").setParameter("email", username).uniqueResult();
    }
}
