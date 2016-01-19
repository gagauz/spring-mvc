package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Seller;
import org.springframework.stereotype.Service;

@Service
public class SellerDao extends AbstractDao<Integer, Seller> {

    public Seller findByEmail(String username) {
        return (Seller) createQuery("select a from Seller a where email=:email").setParameter("email", username).uniqueResult();
    }

}
