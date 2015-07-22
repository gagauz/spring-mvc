package org.webservice.database.model;

import org.webservice.database.model.base.Indexed;
import org.webservice.database.model.base.Model;

import javax.persistence.*;

@Entity
@Table(name = "SUBJECT_EXTRA",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_id", "idx"})
        })
public class SubjectExtra extends Model implements Indexed, Comparable<SubjectExtra> {
    private static final long serialVersionUID = 3164107328342853901L;

    private int idx = -1;
    private int sortId;
    private Subject subject;
    private String name;
    private boolean visible = true;

    @Override
    @Column(nullable = false, updatable = false)
    public int getIdx() {
        return idx;
    }

    public void setIdx(int index) {
        this.idx = index;
    }

    @Column(nullable = false)
    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    @Transient
    public int compareTo(SubjectExtra o) {
        return null != o ? sortId - o.sortId : -1;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + sortId;
    }

    @PrePersist
    protected void onCreate() {
        if (idx == -1) {
            idx = subject.getExtras().size() - 1;
        }
    }

}
