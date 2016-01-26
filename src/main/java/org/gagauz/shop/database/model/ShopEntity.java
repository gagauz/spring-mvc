package org.gagauz.shop.database.model;

import org.gagauz.hibernate.model.UpdatableModel;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class ShopEntity extends UpdatableModel {
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(updatable = false)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
