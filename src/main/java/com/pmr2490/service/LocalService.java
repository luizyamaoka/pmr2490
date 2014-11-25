package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Local;

public interface LocalService {

	public List<Local> getAll() throws Exception;
	
	public Local get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public List<String> create(String name) throws Exception;
	
	public List<String> update(int id, String name) throws Exception;
}
