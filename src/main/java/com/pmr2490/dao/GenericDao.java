package com.pmr2490.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GenericDao<T, PK extends Serializable> {

	private Class<T> type;
	protected SessionFactory sessionFactory;
	
	public GenericDao(SessionFactory sessionFactory, Class<T> type) {
		this.sessionFactory = sessionFactory;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public PK create(T object) {
		
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			PK pk = (PK) session.save(object);
			transaction.commit();
			return pk;
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			T object = (T)session.get(type, id);
			transaction.commit();
			return object;
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(type);
			List<T> objects = cr.list();
			transaction.commit();
			return objects;
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
		return null;
	}

	public void update(T object) {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			session.update(object);
			transaction.commit();
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
	}

	public void delete(T object) {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			session.delete(object);
			transaction.commit();
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
	}
}
