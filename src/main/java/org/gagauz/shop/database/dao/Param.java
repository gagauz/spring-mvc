package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Order;
import org.hibernate.Query;

public class Param {

    private static final String modelPackage = Order.class.getPackage().getName();

    private final String name;
    private final Object value;

    public Param(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public void update(Query query) {
        if (null != value && value.getClass().getName().startsWith(modelPackage)) {
            query.setEntity(name, value);
        } else {
            query.setParameter(name, value);
        }
    }

}
