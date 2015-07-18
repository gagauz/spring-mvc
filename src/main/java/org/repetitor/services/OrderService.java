package org.repetitor.services;

import org.repetitor.database.dao.OrderLogDao;
import org.repetitor.database.model.Order;
import org.repetitor.database.model.OrderLog;
import org.repetitor.database.model.base.OrderChangeStatusHandler;
import org.repetitor.database.model.enums.MessageType;
import org.repetitor.database.model.enums.OrderLogType;
import org.repetitor.database.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderChangeStatusHandler {

    @Autowired
    private OrderLogDao orderLogDao;

    @Autowired
    private MessageCreationService mailCreationService;

    public OrderService() {
        Order.setChangeStatusHandler(this);
    }

    @Override
    public void handle(Order order, OrderStatus newStatus) {
        OrderLog log = new OrderLog();
        log.setOrder(order);
        log.setType(OrderLogType.STATUS);
        log.setMessage(order.getStatus() + " -> " + newStatus);
        log.setVisible(false);
        orderLogDao.saveNoCommit(log);

        if (newStatus == OrderStatus.RECOMMENDED) {
            if (null == order.getOwner()) {
                throw new IllegalStateException("Order owner canot be null with status RECOMMENDED!");
            }
            if (order.getOwner().isNotifyOrders()) {
                mailCreationService.createSmsAndEmail(MessageType.ORDER_RECOMMEND, order, order.getOwner(), null);
            }
        }

        if (newStatus == OrderStatus.CONTACT_SENT) {
            if (null == order.getOwner()) {
                throw new IllegalStateException("Order owner canot be null with status CONTACT_SENT!");
            }
            mailCreationService.createSmsAndEmail(MessageType.CONTACT_SENT, order, order.getOwner(), null);
        }

        if (newStatus == OrderStatus.PAYED) {
            if (null == order.getOwner()) {
                throw new IllegalStateException("Order owner canot be null with status PAYED!");
            }
            mailCreationService.createEmail(MessageType.LEAVE_COMMENT, order, null, order.getPupil());
        }
    }
}
