package org.webservice.database.model;

import org.webservice.database.model.base.OrderChangeStatusHandler;
import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.OrderStatus;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order extends UpdatableModel implements Serializable {

    private static final long serialVersionUID = 1719421850725346271L;

    private static OrderChangeStatusHandler STATUS_CHANGE_HANDLER;

    private Buyer buyer;
    private AddressData addressData;
    private String phone;
    private Product product;
    private List<OrderItem> items;
    private OrderStatus status = OrderStatus.OPEN;
    private BigDecimal amountProducts = BigDecimal.ZERO;
    private BigDecimal amountShipping = BigDecimal.ZERO;
    private BigDecimal amountVat = BigDecimal.ZERO;
    private BigDecimal amountTotal = BigDecimal.ZERO;
    private List<OrderLog> orderLogs;

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Embedded
    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    @Column(nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getAmountProducts() {
        return amountProducts;
    }

    public void setAmountProducts(BigDecimal amountProducts) {
        this.amountProducts = amountProducts;
    }

    public BigDecimal getAmountShipping() {
        return amountShipping;
    }

    public void setAmountShipping(BigDecimal amountShipping) {
        this.amountShipping = amountShipping;
    }

    public BigDecimal getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(BigDecimal amountVat) {
        this.amountVat = amountVat;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean changeStatus(OrderStatus newStatus) {
        while (null == STATUS_CHANGE_HANDLER) {
            //Wait for app initialization
        }
        if (newStatus != this.status && this.status.getNextStatuses().contains(newStatus)) {
            STATUS_CHANGE_HANDLER.handle(this, newStatus);
            this.status = newStatus;
        }
        return newStatus == this.status;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.ALL})
    public List<OrderLog> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLog> orderLogs) {
        this.orderLogs = orderLogs;
    }

    public static void setChangeStatusHandler(OrderChangeStatusHandler handler) {
        if (null == handler) {
            throw new IllegalStateException("OrderChangeStatusHandler cannot be null!");
        }
        STATUS_CHANGE_HANDLER = handler;
    }

}
