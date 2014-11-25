package com.pmr2490.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDao<T, PK extends Serializable> {

	private Class<T> type;
	protected SessionFactory sessionFactory;
	
	public GenericDao(SessionFactory sessionFactory, Class<T> type) {
		this.sessionFactory = sessionFactory;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public PK create(T object) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		PK pk = (PK) session.save(object);
		return pk;
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		T object = (T)session.get(type, id);
		return object;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(type);
		List<T> objects = cr.list();
		return objects;
	}

	public void update(T object) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		session.update(object);
	}

	public void delete(T object) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		session.delete(object);
	}
}
