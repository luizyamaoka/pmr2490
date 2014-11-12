package com.pmr2490.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.UserDao;
import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
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
	public void create(String firstName, String lastName, Date birthDate,
			String genre, Integer phoneDdd, String phoneNumber, String email,
			String password, boolean isPromoter, College college,
			Profession profession) {
		User user = new User(null, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, password, isPromoter, college, profession);
		
		this.userDao.create(user);
		
	}

	@Override
	public void update(int id, String firstName, String lastName,
			Date birthDate, String genre, Integer phoneDdd, String phoneNumber,
			String email, boolean isPromoter, College college,
			Profession profession) {
		User user = new User(id, firstName, lastName, birthDate, genre, phoneDdd, phoneNumber, email, isPromoter, college, profession);
		this.userDao.update(user);
		
	}

}
