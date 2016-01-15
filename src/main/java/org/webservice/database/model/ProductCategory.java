package org.webservice.database.model;

import org.webservice.database.model.base.UpdatableModel;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends UpdatableModel {
    private static final long serialVersionUID = -14458707898267529L;

    private ShopCategory shopCategory;
    private String name;
    private String externalId;
    private ProductCategory parent;
    private List<ProductCategory> children;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

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
