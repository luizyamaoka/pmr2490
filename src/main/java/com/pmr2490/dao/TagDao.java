package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Tag;

@Repository
public class TagDao extends GenericDao<Tag, Integer> {

	@Autowired
	public TagDao(SessionFactory sessionFactory) {
		super(sessionFactory, Tag.class);
	}
	
}
