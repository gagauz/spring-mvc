package org.gagauz.shop.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.gagauz.hibernate.model.Model;

@Entity
@Table(name = "PRODUCT_ATTRIBUTE")
public class ProductAttribute extends Model {
    private String key;
    private String name;
    private String description;
    private String data;

    @Column(nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String name) {
        this.key = name;
    }

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
