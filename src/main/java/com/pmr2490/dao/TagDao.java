package com.pmr2490.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Tag;

@Repository
public class TagDao extends GenericDao<Tag, Integer> {

	@Autowired
	public TagDao(SessionFactory sessionFactory) {
		super(sessionFactory, Tag.class);
	}
	
	public Tag getByName(String name) throws Exception {
		Session session = super.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Tag.class);
		criteria.add(Restrictions.eq("name", name));
		return (Tag)criteria.uniqueResult();
	}
	
}
