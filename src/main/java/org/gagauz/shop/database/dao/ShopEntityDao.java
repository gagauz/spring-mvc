package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.ShopEntity;

import java.io.Serializable;
import java.util.List;

public abstract class ShopEntityDao<Id extends Serializable, E extends ShopEntity> extends AbstractDao<Id, E> {

    private String entityName;

    public ShopEntityDao() {
        super();
        entityName = entityClass.getSimpleName();
    }

    public List<E> findByShop(Shop shop) {
        return createQuery("select e from " + entityName + " e where e.shop=:shop")
                .setEntity("shop", shop)
                .list();
    }
}
