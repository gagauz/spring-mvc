package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Payment;
import org.webservice.database.model.Shop;

import java.util.List;

@Service
public class PaymentDao extends AbstractDao<Integer, Payment> {

    public List<Payment> findByRepetitor(Shop shop) {
        return createQuery("select p from Payment p where p.shop=:shop")
                .setEntity("repetitor", shop).list();
    }

}
