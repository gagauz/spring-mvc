package org.gagauz.shop.web.base;

import org.gagauz.hibernate.dao.AbstractDao;

import java.io.Serializable;

public abstract class BasePage<E> {

    private E object;

    private AbstractDao<Serializable, E> getDao() {
        return AbstractDao.DAO_MAP.get(null);
    }

    void onActivate(E entity) {
        this.object = entity;
    }

    void onEdit(E entity) {
        this.object = entity;
    }

}
