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
	public List<User> index() {
		return this.userDao.index();
	}

	@Override
	public User findById(int id) {
		return this.userDao.findById(id);
	}

	@Override
	public void destroy(int id) {
		User user = this.userDao.findById(id);
		this.userDao.destroy(user);
	}

	@Override
	public void create(String name) {
		User user = new User();
		user.setName(name);
		
		this.userDao.create(user);
	}

	@Override
	public void edit(int id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		
		this.userDao.edit(user);
	}

}
