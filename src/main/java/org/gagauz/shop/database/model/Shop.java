package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;

import javax.persistence.*;

@Entity
@Table(name = "SHOP")
public class Shop extends UpdatableModel {
    private String name;
    private String description;
    private String host;
    private ShopCategory category;
    private Seller seller;

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

    @Column(nullable = false)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public ShopCategory getCategory() {
        return category;
    }

    public void setCategory(ShopCategory category) {
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

}
