package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Profession;

@Repository
public class ProfessionDao extends GenericDao<Profession, Integer> {

	@Autowired
	public ProfessionDao(SessionFactory sessionFactory) {
		super(sessionFactory, Profession.class);
	}
	
}
