package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.College;

@Repository
public class CollegeDao extends GenericDao<College, Integer> {

	@Autowired
	public CollegeDao(SessionFactory sessionFactory) {
		super(sessionFactory, College.class);
	}
	
	public College getByName(String name) throws Exception {
		Session session = super.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(College.class);
		criteria.add(Restrictions.eq("name", name));
		return (College)criteria.uniqueResult();
	}
}
