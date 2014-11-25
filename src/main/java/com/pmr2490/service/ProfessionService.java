package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Profession;

public interface ProfessionService {

	public List<Profession> getAll() throws Exception;
	
	public Profession get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public List<String> create(String name) throws Exception;
	
	public List<String> update(int id, String name) throws Exception;
	
}
