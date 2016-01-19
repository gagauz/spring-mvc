package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem extends UpdatableModel {
    private static final long serialVersionUID = 1925248489031919078L;
    private Order order;
    private Product product;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
