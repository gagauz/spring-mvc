package org.gagauz.shop.database.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gagauz.shop.database.model.base.UpdatableModel;

@Entity
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends UpdatableModel {
    private static final long serialVersionUID = -14458707898267529L;

    private String name;
    private String externalId;
    private ProductCategory parent;
    private List<ProductCategory> children;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = true)
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        if (null != parent) {
            if (parent.equals(this)) {
                throw new IllegalStateException("ProductCategory cannot be parent to itself.");
            }
            //                    parent.getChildren().add(this);
        }
        //                if (null != this.parent) {
        //                    this.parent.getChildren().remove(this);
        //                }
        this.parent = parent;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

}
