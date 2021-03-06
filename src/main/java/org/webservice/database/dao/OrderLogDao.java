package org.webservice.database.dao;

import org.springframework.stereotype.Service;
import org.webservice.database.model.OrderLog;

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
