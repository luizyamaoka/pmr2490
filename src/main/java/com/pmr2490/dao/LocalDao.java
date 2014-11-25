package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Local;

@Repository
public class LocalDao extends GenericDao<Local, Integer> {

	@Autowired
	public LocalDao(SessionFactory sessionFactory) {
		super(sessionFactory, Local.class);
	}
	
	public Local getByName(String name) throws Exception {
		Session session = super.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Local.class);
		criteria.add(Restrictions.eq("name", name));
		return (Local)criteria.uniqueResult();
	}
}
