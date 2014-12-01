package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Event;
import com.pmr2490.model.User;
import com.pmr2490.model.Participant;

@Repository
public class UserDao extends GenericDao<User, Integer>  {

	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}
	
	public User getByEmail(String email) throws Exception {
		Session session = null;
		session = super.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("email", email));
		User user = (User) cr.uniqueResult();
		return user;
	}
	
	public User getEagerByEmail(String email) throws Exception {
		Session session = null;
		session = super.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("email", email));
		User user = (User) cr.uniqueResult();
		for (Event event : user.getEvents())
			event.getName();
		for (Participant participant : user.getParticipations())
			participant.getId();
		return user;

	}
	
}
