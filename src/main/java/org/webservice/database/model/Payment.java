package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.PaymentStatus;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "PAYMENT")
public class Payment extends UpdatableModel {

    private static final long serialVersionUID = 2820814378379992398L;
    private Shop shop;
    private Order order;
    private String subject;
    private BigDecimal amount = BigDecimal.ZERO;
    private Currency currency;
    private PaymentStatus status = PaymentStatus.PENDING;
    private String externalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(nullable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Column(nullable = true)
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
