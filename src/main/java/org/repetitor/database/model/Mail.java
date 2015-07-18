package org.repetitor.database.model;

import org.repetitor.database.model.enums.MailStatus;

import javax.persistence.*;

@Entity
@Table(name = "MAIL")
public class Mail extends Message {
    private static final long serialVersionUID = 7343862671667686824L;
    private String from;
    private String to;
    private MailStatus status = MailStatus.CREATED;

    @Column(nullable = false, name = "from1")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(nullable = false, name = "to1")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public MailStatus getStatus() {
        return status;
    }

    public void setStatus(MailStatus status) {
        this.status = status;
    }

    @Override
    @Column(nullable = false)
    @Lob
    public String getBody() {
        return super.getBody();
    }

    @Override
    public String toString() {
        return "Mail <id=" + id + ", from=" + from + ", to=" + to + ", type=" + getType() + ">";
    }
}
