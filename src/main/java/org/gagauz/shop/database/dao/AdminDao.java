package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminDao extends AbstractDao<Integer, Admin> {

    public Admin findByEmail(String username) {
        return (Admin) createQuery("select a from Admin a where email=:email").setParameter("email", username).uniqueResult();
    }

}
