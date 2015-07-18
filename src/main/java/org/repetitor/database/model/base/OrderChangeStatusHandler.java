package org.repetitor.database.model.base;

import org.repetitor.database.model.Order;
import org.repetitor.database.model.enums.OrderStatus;

public interface OrderChangeStatusHandler {
    void handle(Order order, OrderStatus newStatus);
}
