package org.webservice.database.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.PaymentStatus;

@Entity
@Table(name = "PAYMENT")
public class Payment extends UpdatableModel {

    private static final long serialVersionUID = 2820814378379992398L;
    private Repetitor repetitor;
    private Order order;
    private Manager manager;
    private String subject;
    private BigDecimal amount = BigDecimal.ZERO;
    private PaymentStatus status = PaymentStatus.PENDING;
    private String externalId;

    // private ChronopayPaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Repetitor getRepetitor() {
        return repetitor;
    }

    public void setRepetitor(Repetitor repetitor) {
        this.repetitor = repetitor;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
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
