package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Tagging;

@Repository
public class TaggingDao extends GenericDao<Tagging, Integer> {

	@Autowired
	public TaggingDao(SessionFactory sessionFactory) {
		super(sessionFactory, Tagging.class);
	}
	
}
