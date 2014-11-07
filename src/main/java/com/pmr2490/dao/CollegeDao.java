package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.College;

@Repository
public class CollegeDao extends GenericDao<College, Integer> {

	@Autowired
	public CollegeDao(SessionFactory sessionFactory) {
		super(sessionFactory, College.class);
	}
	
}
