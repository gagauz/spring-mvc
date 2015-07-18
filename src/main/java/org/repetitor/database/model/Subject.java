package org.repetitor.database.model;

import org.repetitor.database.model.base.Parent;
import org.repetitor.database.model.base.UpdatableModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SUBJECT")
public class Subject extends UpdatableModel implements Serializable, Parent {
    private static final long serialVersionUID = -7008136190603048576L;

    private String name;
    private String name2;
    private Subject parent;
    private List<Subject> children;
    private int sortId;
    private boolean visible = true;
    private boolean container;

    private Set<SubjectExtra> extras = new LinkedHashSet<SubjectExtra>();
    private Set<SubjectSection> sections = new LinkedHashSet<SubjectSection>();

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Subject.class, optional = true)
    // @ForeignKey(name = "fk_Subject_Parent")
    public Subject getParent() {
        return parent;
    }

    public void setParent(Subject parent) {
        this.parent = parent;
    }

    @Override
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", targetEntity = Subject.class)
    public List<Subject> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
    }

    @Column(nullable = false)
    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    @Column(nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean enabled) {
        this.visible = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = {CascadeType.ALL})
    @OrderColumn(name = "idx")
    public Set<SubjectExtra> getExtras() {
        return extras;
    }

    public void setExtras(Set<SubjectExtra> extras) {
        this.extras = extras;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = {CascadeType.ALL})
    @OrderColumn(name = "idx")
    public Set<SubjectSection> getSections() {
        return sections;
    }

    public void setSections(Set<SubjectSection> sections) {
        this.sections = sections;
    }

    @Column(nullable = false)
    public boolean isContainer() {
        return container;
    }

    public void setContainer(boolean container) {
        this.container = container;
    }

    @Transient
    public String getHierarchyName() {
        StringBuilder sb = new StringBuilder();
        if (parent != null) {
            sb.append(parent.getHierarchyName()).append(" -> ");
        }
        sb.append(name);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Subject: {id:" + id + ", name:" + name + "}";
    }

}
