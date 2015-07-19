package org.repetitor.database.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.repetitor.database.model.base.Model;
import org.repetitor.database.model.enums.RegionType;

@Entity
@Table(name = "REGION")
public class Region extends Model {
    private static final long serialVersionUID = -7982792659372409041L;
    public static final Region DEFAULT = new Region();
    static {
        DEFAULT.setId(0);
        DEFAULT.setName("Регион не найден");
    }

    private RegionType type;
    @JsonBackReference
    private Region parent;
    @JsonManagedReference
    private List<Region> children;

    private String name;
    private String name2;

    @ManyToOne(fetch = FetchType.LAZY)
    // @ForeignKey(name = "fk_Region_Parent")
    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        // if (null != parent && null != type && type.getParent() !=
        // parent.getType()) {
        // throw new IllegalStateException("Incopatible parent region type");
        // }
        this.parent = parent;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public RegionType getType() {
        return type;
    }

    public void setType(RegionType type) {
        this.type = type;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 100)
    public String getName2() {
        return name2;
    }

    public void setName2(String name) {
        this.name2 = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }

    @Transient
    public Region getFirstParentOfType(RegionType type) {
        if (this.type == type || null == parent) {
            return this;
        }
        return parent.getFirstParentOfType(type);
    }

}
