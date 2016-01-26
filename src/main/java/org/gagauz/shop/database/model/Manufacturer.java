package org.gagauz.shop.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MANUFACTURER", uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "name"}) )
public class Manufacturer extends ShopEntity {
    private static final long serialVersionUID = 5337755365132817460L;
    private String name;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
