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
	
	public User getByEmail(String email) {
		return this.userDao.getByEmail(email);
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
			String email, Boolean isPromoter, College college,
			Profession profession, String password) {
		User user = this.userDao.get(id);
		
		if (firstName != null) user.setFirstName(firstName);
		if (lastName != null) user.setLastName(lastName);
		if (birthDate != null) user.setBirthDate(birthDate);
		if (genre != null) user.setGenre(genre);
		if (phoneDdd != null) user.setPhoneDdd(phoneDdd);
		if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
		if (email != null) user.setEmail(email);
		if (isPromoter != null) user.setPromoter(isPromoter);
		if (college != null) user.setCollege(college);
		if (profession != null) user.setProfession(profession);
		if (password != null) user.setPassword(password);
		
		this.userDao.update(user);
		
	}

}
