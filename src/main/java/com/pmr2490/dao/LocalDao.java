package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Local;

@Repository
public class LocalDao extends GenericDao<Local, Integer> {

	@Autowired
	public LocalDao(SessionFactory sessionFactory) {
		super(sessionFactory, Local.class);
	}
}
