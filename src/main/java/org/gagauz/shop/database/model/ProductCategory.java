package org.gagauz.shop.database.model;

import org.gagauz.shop.utils.Parent;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "PRODUCT_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "externalId"}) )
public class ProductCategory extends ShopEntity implements Parent<ProductCategory> {
    private static final long serialVersionUID = -14458707898267529L;

    private String name;
    private transient String hierarchyName;
    private String externalId;
    private ProductCategory parent;
    private List<ProductCategory> children;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.hierarchyName = null;

    }

    @Column(nullable = true)
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String shopId) {
        this.externalId = shopId;
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

    @Override
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

    @Transient
    public String getHierarchyName() {
        if (null == hierarchyName) {
            hierarchyName = name;
            if (null != parent) {
                hierarchyName = parent.getHierarchyName() + '/' + hierarchyName;
            }
        }
        return hierarchyName;
    }
}
