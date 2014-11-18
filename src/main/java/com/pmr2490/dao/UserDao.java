package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.User;

@Repository
public class UserDao extends GenericDao<User, Integer>  {

	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}
	
	public User getByEmail(String email) {
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = super.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("email", email));
			User user = (User) cr.uniqueResult();
			transaction.commit();
			return user;
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
	
}
