package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.enums.Currency;
import org.gagauz.shop.database.model.enums.ProductUnit;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "article"}) )
public class Product extends ShopEntity {
    private static final long serialVersionUID = 5735037622492024586L;
    private Manufacturer manufacturer;
    private ProductCategory category;
    private String name;
    private String article;
    private String description;
    private String images;
    private List<ProductAttribute> attributes;
    private List<ProductGroup> groups;
    private List<ProductVariant> variants;
    private ProductUnit unit;
    private BigDecimal price;
    private int discount = 0;
    private Currency currency;
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
    @JoinTable(name = "PRODUCT_TO_ATTRIBUTE", joinColumns = @JoinColumn(name = "productId", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "attributeId", referencedColumnName = "id") )
    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCT_VARIANT", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id") )
    public List<ProductGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ProductGroup> groups) {
        this.groups = groups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.product", cascade = CascadeType.ALL)
    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }

    @Column(nullable = false, precision = 10, scale = 2)
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

    @Column(nullable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Transient
    public String getImageUrl() {
        return getImageUrls().length > 0 ? getImageUrls()[0] : "no_image.jpg";
    }

    @Transient
    public String[] getImageUrls() {
        if (null == imageUrls) {
            imageUrls = images.split(",");
        }
        return imageUrls;
    }

    @Transient
    public ProductVariant getVariant(ProductGroup group) {
        for (ProductVariant v : getVariants()) {
            if (v.getGroup().equals(group)) {
                return v;
            }
        }
        return null;
    }

}
