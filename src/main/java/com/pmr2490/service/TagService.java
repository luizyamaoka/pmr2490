package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Tag;

public interface TagService {

	public List<Tag> getAll() throws Exception;
	
	public Tag get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public List<String> create(String name) throws Exception;
	
	public List<String> update(int id, String name) throws Exception;
}
