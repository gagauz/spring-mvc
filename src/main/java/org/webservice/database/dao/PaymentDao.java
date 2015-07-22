package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.Payment;
import org.webservice.database.model.Repetitor;

import java.util.List;

@Service
public class PaymentDao extends AbstractDao<Integer, Payment> {

    public List<Payment> findByRepetitor(Repetitor repetitor) {
        return createQuery("select p from Payment p where p.repetitor=:repetitor")
                .setEntity("repetitor", repetitor).list();
    }

}
