package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Tag;

public interface TagService {

	public List<Tag> getAll();
	
	public Tag get(int id);
	
	public void delete(int id);
	
	public int create(String name);
	
	public void update(int id, String name);
}
