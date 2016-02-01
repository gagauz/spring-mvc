package org.gagauz.shop.database.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "PRODUCT_VARIANT")
public class ProductVariant implements Serializable {
    private static final long serialVersionUID = -827692914077737023L;

    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 2085832538594201806L;
        private ProductGroup group;
        private Product product;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        public ProductGroup getGroup() {
            return group;
        }

        public void setGroup(ProductGroup group) {
            this.group = group;
        }

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    private Id id;
    private String name;

    public ProductVariant() {

    }

    public ProductVariant(ProductGroup group, Product product) {
        id = new Id();
        id.setGroup(group);
        id.setProduct(product);
    }

    @EmbeddedId
    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public ProductGroup getGroup() {
        return getId().getGroup();
    }

    @Transient
    public Product getProduct() {
        return getId().getProduct();
    }
}
