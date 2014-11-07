package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.User;

public interface UserService {

	public List<User> getAll();
	
	public User get(int id);
	
	public void delete(int id);
	
	public void create(String name);
	
	public void update(int id, String name);
}
