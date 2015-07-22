package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;
import org.webservice.database.model.enums.MessageType;

import javax.persistence.*;

@SuppressWarnings("serial")
@MappedSuperclass
public class Message extends UpdatableModel {
    private MessageType type;
    private String subject;
    private String body;
    private Repetitor repetitor;
    private Order order;

    public Message() {
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Transient
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Repetitor getRepetitor() {
        return repetitor;
    }

    public void setRepetitor(Repetitor repetitor) {
        this.repetitor = repetitor;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
