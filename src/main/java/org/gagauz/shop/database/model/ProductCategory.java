package org.gagauz.shop.database.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.gagauz.shop.database.model.base.UpdatableModel;
import org.gagauz.shop.utils.Parent;

@Entity
@Table(name = "PRODUCT_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "externalId"}) )
public class ProductCategory extends UpdatableModel implements Parent<ProductCategory> {
    private static final long serialVersionUID = -14458707898267529L;

    private Shop shop;
    private String name;
    private transient String hierarchyName;
    private String externalId;
    private ProductCategory parent;
    private List<ProductCategory> children;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(updatable = false)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

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
