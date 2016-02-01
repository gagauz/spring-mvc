package org.gagauz.shop.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRODUCT_ATTRIBUTE", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "name"}) )
public class ProductAttribute extends ShopEntity {
    private static final long serialVersionUID = 4110258121797070663L;
    private String name;
    private String description;
    private String data;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = true)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
