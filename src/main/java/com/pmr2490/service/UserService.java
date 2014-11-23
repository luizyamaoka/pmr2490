package com.pmr2490.service;

import java.util.Date;
import java.util.List;

import com.pmr2490.model.College;
import com.pmr2490.model.Profession;
import com.pmr2490.model.User;

public interface UserService {

	public List<User> getAll() throws Exception;
	
	public User get(int id) throws Exception;
	
	public User getByEmail(String email) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public int create(String firstName, String lastName, Date birthDate, String genre, Integer phoneDdd, 
			String phoneNumber, String email, String password, boolean isPromoter, College college, Profession profession) throws Exception;
	
	public void update(int id, String firstName, String lastName, Date birthDate, String genre, Integer phoneDdd, 
			String phoneNumber, String email, Boolean isPromoter, College college, Profession profession, String password) throws Exception;
}
