package org.webservice.database.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "REPETITOR_VIEW")
public class RepetitorView implements Serializable {
    private static final long serialVersionUID = 6915515341802429427L;

    @Embeddable
    static class Id implements Serializable {
        private static final long serialVersionUID = 1529254876098051135L;
        private int ip;
        private int repetitorId;

        @Column(nullable = false)
        public int getIp() {
            return ip;
        }

        public void setIp(int ip) {
            this.ip = ip;
        }

        @Column(nullable = false)
        public int getRepetitorId() {
            return repetitorId;
        }

        public void setRepetitorId(int repetitorId) {
            this.repetitorId = repetitorId;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (null != obj) && ((Id) obj).ip == ip && ((Id) obj).repetitorId == repetitorId;
        }
    }

    private Id id = new Id();
    private Date created = new Date();

    @EmbeddedId
    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
