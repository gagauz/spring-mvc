package org.gagauz.shop.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gagauz.hibernate.model.UpdatableModel;

@Entity
@Table(name = "BASKET_ITEM")
public class BasketItem extends UpdatableModel {
    private static final long serialVersionUID = -8981723147874310712L;
    private Buyer buyer;
    private Product product;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
