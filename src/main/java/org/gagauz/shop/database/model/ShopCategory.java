package org.gagauz.shop.database.model;

import org.gagauz.shop.database.model.base.UpdatableModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SHOP_CATEGORY")
public class ShopCategory extends UpdatableModel {
    private static final long serialVersionUID = 673683659400164414L;

    private String name;
    private String externalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

}
