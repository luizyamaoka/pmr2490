package com.pmr2490.service;

import java.util.List;

import com.pmr2490.dto.UserDto;
import com.pmr2490.model.User;

public interface UserService {

	public List<User> getAll() throws Exception;
	
	public User get(int id) throws Exception;
	
	public User getByEmail(String email) throws Exception;
	
	public User getEagerByEmail(String email) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public void updatePassword(int id, String password) throws Exception;
	
	public List<String> create(UserDto userDto) throws Exception;
	
	public List<String> update(UserDto userDto) throws Exception;

}
