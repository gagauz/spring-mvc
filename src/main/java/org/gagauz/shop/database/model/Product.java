package org.gagauz.shop.database.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product extends ShopEntity {
    private static final long serialVersionUID = 5735037622492024586L;
    private Manufacturer manufacturer;
    private ProductCategory category;
    private String name;
    private String article;
    private String description;
    private String images;
    private List<ProductAttribute> attributes;
    private BigDecimal price;
    private int discount = 0;
    private transient String[] imageUrls;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCT_2_ATTRIBUTE")
    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    @Column(nullable = false, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Transient
    public String[] getImageUrls() {
        if (null == imageUrls) {
            imageUrls = images.split(",");
        }
        return imageUrls;
    }

}
