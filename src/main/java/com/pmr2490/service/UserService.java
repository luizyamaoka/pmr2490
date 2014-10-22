package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.User;

public interface UserService {

	public List<User> index();
	
	public User findById(int id);
	
	public void destroy(int id);
	
	public void create(String name);
	
	public void edit(int id, String name);
}
