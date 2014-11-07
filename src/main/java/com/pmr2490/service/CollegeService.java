package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.College;

public interface CollegeService {
	
	public List<College> getAll();
	
	public College get(int id);
	
	public void delete(int id);
	
	public int create(String name);
	
	public void update(int id, String name);
}
