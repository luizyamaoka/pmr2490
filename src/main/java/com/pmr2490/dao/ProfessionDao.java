package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Profession;

@Repository
public class ProfessionDao extends GenericDao<Profession, Integer> {

	@Autowired
	public ProfessionDao(SessionFactory sessionFactory) {
		super(sessionFactory, Profession.class);
	}
	
	public Profession getByName(String name) throws Exception {
		Session session = super.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Profession.class);
		criteria.add(Restrictions.eq("name", name));
		return (Profession)criteria.uniqueResult();
	}
	
}
