package org.repetitor.database.dao;

import org.repetitor.database.model.OrderLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLogDao extends AbstractDao<Integer, OrderLog> {

    public List<OrderLog> findByOrderId(int orderId, boolean visible) {
        String hql = "select o from OrderLog o where o.order.id=:id";
        if (visible) {
            hql += " and o.visible=true";
        }

        return createQuery(hql).setInteger("id", orderId).list();
    }
}
