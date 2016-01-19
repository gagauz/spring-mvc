package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product extends UpdatableModel {
    private static final long serialVersionUID = 5735037622492024586L;
    private Shop shop;
    private ProductCategory productCategory;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
