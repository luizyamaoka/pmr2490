package com.pmr2490.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GenericDao<T, PK extends Serializable> {

	private Class<T> type;
	private SessionFactory sessionFactory;
	
	public GenericDao(SessionFactory sessionFactory, Class<T> type) {
		this.sessionFactory = sessionFactory;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public PK create(T object) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		PK pk = (PK) session.save(object);
		transaction.commit();
		return pk;
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		T object = (T)session.get(type, id);
		transaction.commit();
		return object;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria cr = session.createCriteria(type);
		List<T> objects = cr.list();
		transaction.commit();
		return objects;
	}

	public void update(T object) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.update(object);
		transaction.commit();
	}

	public void delete(T object) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(object);
		transaction.commit();
	}
}
