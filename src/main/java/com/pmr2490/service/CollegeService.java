package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.College;

public interface CollegeService {
	
	public List<College> getAll() throws Exception;
	
	public College get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public List<String> create(String name) throws Exception;
	
	public List<String> update(int id, String name) throws Exception;
}
