package org.gagauz.shop.database.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRODUCT_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "name"}) )
public class ProductGroup extends ShopEntity {
    private static final long serialVersionUID = -2888920990834739351L;
    private String name;
    private List<Product> products;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCT_VARIANT", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id") )
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
