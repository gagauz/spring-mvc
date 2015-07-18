package org.repetitor.database.model;

import org.repetitor.database.model.base.UpdatableModel;
import org.repetitor.database.model.enums.MessageType;

import javax.persistence.*;

@Entity
@Table(name = "MAIL_TEMPLATE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sms", "type"})
})
public class MessageTemplate extends UpdatableModel {
    private static final long serialVersionUID = 4921762296473317435L;
    private boolean sms;
    private MessageType type;
    private String subjectTemplate;
    private String bodyTemplate;

    @Column(nullable = false)
    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
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
    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    @Column(nullable = false, length = 10000)
    @Lob
    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

}
