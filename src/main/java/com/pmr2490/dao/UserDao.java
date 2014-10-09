package com.pmr2490.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.User;

@Repository
public class UserDao {

	private SessionFactory sessionFactory;
	
	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<User> index() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Criteria cr = session.createCriteria(User.class);
		
		@SuppressWarnings("unchecked")
		List<User> users = cr.list();
		
		tx.commit();
		return users;
	}
	
}
