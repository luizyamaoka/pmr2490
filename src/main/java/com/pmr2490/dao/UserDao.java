package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.User;

@Repository
public class UserDao extends GenericDao<User, Integer>  {

	@Autowired
	public UserDao(SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}
	
}
