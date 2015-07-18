package org.repetitor.database.dao;

import org.repetitor.database.model.Payment;
import org.repetitor.database.model.Repetitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentDao extends AbstractDao<Integer, Payment> {

    public List<Payment> findByRepetitor(Repetitor repetitor) {
        return createQuery("select p from Payment p where p.repetitor=:repetitor")
                .setEntity("repetitor", repetitor).list();
    }

}
