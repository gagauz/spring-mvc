package org.gagauz.shop.database.model.base;

import org.gagauz.shop.database.model.Order;
import org.gagauz.shop.database.model.enums.OrderStatus;

public interface OrderChangeStatusHandler {
    void handle(Order order, OrderStatus newStatus);
}
