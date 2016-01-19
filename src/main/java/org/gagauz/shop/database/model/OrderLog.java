package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;
import org.gagauz.shop.database.model.enums.OrderLogType;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ORDER_LOG")
public class OrderLog extends UpdatableModel implements Serializable {

    private static final long serialVersionUID = 1719421850725346271L;

    private Order order;
    private String message;
    private boolean visible;
    private OrderLogType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order request) {
        this.order = request;
    }

    @Column(length = 1000, nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    public OrderLogType getType() {
        return type;
    }

    public void setType(OrderLogType type) {
        this.type = type;
    }

}
