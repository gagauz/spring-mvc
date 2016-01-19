package org.gagauz.shop.database.dao;

import org.gagauz.shop.database.model.Order;
import org.gagauz.shop.database.model.enums.OrderStatus;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDao extends AbstractDao<Integer, Order> {

    public List<Order> findAssigned() {
        String hql = "select o from Order o where o.owner is not null";
        Query q = createQuery(hql);
        return q.list();
    }

    public Order findFetchedById(int orderId) {
        String hql = "select o from Order o " +
                "left join fetch o.subject sb " +
                "left join fetch o.subject.extras ex " +
                "left join fetch o.subject.sections sc " +
                "left join fetch o.placePupilRegion rg " +
                "where o.id=:id";

        return (Order) createQuery(hql).setInteger("id", orderId).uniqueResult();
    }

    public List<Order> findFetchedByRepetitorId(int id) {
        String hql = "select distinct o from Order o " +
                "left join fetch o.subject sb " +
                "left join fetch o.subject.extras ex " +
                "left join fetch o.subject.sections sc " +
                "left join fetch o.placePupilRegion rg " +
                "where o.owner.id=:id";

        return createQuery(hql).setInteger("id", id).list();
    }

    public List<Order> findRecommendedForRepetitor(int id) {
        String hql = "select o from Order o " +
                "left join fetch o.subject sb " +
                "left join fetch o.placePupilRegion rg " +
                "where o.owner.id=:id and o.status=:status";

        return createQuery(hql)
                .setInteger("id", id)
                .setString("status", OrderStatus.RECOMMENDED.name()).list();
    }
}
