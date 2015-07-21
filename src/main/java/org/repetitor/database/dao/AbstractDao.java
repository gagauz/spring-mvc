package org.repetitor.database.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gagauz.utils.Function;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

//@Transactional
public class AbstractDao<I extends Serializable, E> {

	public static final Map<Class<?>, AbstractDao<?, ?>> daoMap = new HashMap<Class<?>, AbstractDao<?, ?>>();

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
		daoMap.put(entityClass, this);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSession(Session session) {

	}

	@SuppressWarnings("unchecked")
	public I getIdentifier(E entity) {
		return (I) getSession().getIdentifier(entity);
	}

	@SuppressWarnings("unchecked")
	public E findById(I id) {
		return (E) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public E loadById(I id) {
		return (E) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<E> findByIds(Collection<I> ids) {
		return getSession().createCriteria(entityClass).add(Restrictions.in("id", ids)).list();
	}

	public Query createQuery(String queryString) {
		return getSession().createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return getSession().createSQLQuery(queryString);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		return getSession().createCriteria(entityClass).list();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByFilter(final EntityFilter filter) {
		return filter.setCriteria(getSession().createCriteria(entityClass)).list();
	}

	public long countByFilter(final EntityFilter filter) {
		return (Long) filter.setCriteria(getSession().createCriteria(entityClass).setProjection(Projections.rowCount())).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<E> findByFilter(final String sql, final HqlEntityFilter filter) {
		Query query = filter.createQuery(new Function<String, Query>() {
			@Override
			public Query call(String arg0) {
				return createQuery(arg0);
			}
		}, sql);

		return query.list();
	}

	public Criteria createCriteria() {
		return getSession().createCriteria(entityClass);
	}

	@SuppressWarnings("rawtypes")
	public List findByQuery(String hql, Param... params) {
		Query query = getSession().createQuery(hql);
		for (Param param : params) {
			param.update(query);
		}
		return query.list();
	}

	public void merge(E entity) {
		getSession().merge(entity);
	}

	public void saveNoCommit(E entity) {
		getSession().saveOrUpdate(entity);
	}

	public void save(E entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();
	}

	public void save(Collection<E> entities) {
		for (E entity : entities) {
			getSession().saveOrUpdate(entity);
		}
		getSession().flush();
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void evict(E entity) {
		getSession().evict(entity);
	}

	public void flush() {
		getSession().flush();
	}

}
