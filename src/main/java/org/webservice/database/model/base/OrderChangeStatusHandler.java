package org.webservice.database.model.base;

import org.webservice.database.model.Order;
import org.webservice.database.model.enums.OrderStatus;

public interface OrderChangeStatusHandler {
    void handle(Order order, OrderStatus newStatus);
}
