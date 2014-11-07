package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.UserDao;
import com.pmr2490.model.User;
import com.pmr2490.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public List<User> getAll() {
		return this.userDao.getAll();
	}

	@Override
	public User get(int id) {
		return this.userDao.get(id);
	}

	@Override
	public void delete(int id) {
		User user = this.userDao.get(id);
		this.userDao.delete(user);
	}

	@Override
	public void create(String name) {
		User user = new User();
		user.setFirstName(name);
		
		this.userDao.create(user);
	}

	@Override
	public void update(int id, String name) {
		User user = new User();
		user.setId(id);
		user.setFirstName(name);
		
		this.userDao.update(user);
	}

}
