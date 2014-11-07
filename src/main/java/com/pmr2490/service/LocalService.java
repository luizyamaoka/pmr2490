package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Local;

public interface LocalService {

	public List<Local> getAll();
	
	public Local get(int id);
	
	public void delete(int id);
	
	public int create(String name);
	
	public void update(int id, String name);
}
