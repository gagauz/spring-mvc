package org.repetitor.database.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.sql.JoinType;
import org.repetitor.database.model.Order;
import org.repetitor.database.model.Repetitor;
import org.repetitor.database.model.Subject;
import org.repetitor.database.model.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.criterion.Restrictions.*;
import static org.repetitor.database.model.enums.OrderStatus.*;

@Service
public class OrderDao extends AbstractDao<Integer, Order> {
    public List<Order> findUnassigned(Subject subject, Repetitor repetitor) {
        Criteria criteria = createCriteria();
        criteria.createAlias("owner", "owner", JoinType.LEFT_OUTER_JOIN);
        if (null != subject) {
            criteria.createAlias("subject", "sb", JoinType.INNER_JOIN).add(eq("subject", subject));
        }

        criteria.add(or(
                and(isNull("owner"), in("status", new OrderStatus[] {OPEN, CONFIRMED_PUPIL})),
                and(eq("owner", repetitor), eq("status", ACCEPTED))
                ));

        return criteria.list();
    }

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
