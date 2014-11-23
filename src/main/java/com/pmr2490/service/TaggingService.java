package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Event;
import com.pmr2490.model.Tag;
import com.pmr2490.model.Tagging;

public interface TaggingService {

	public List<Tagging> getAll() throws Exception;
	
	public Tagging get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public int create(Event event, Tag tag) throws Exception;
	
	public void update(int id, Event event, Tag tag) throws Exception;
	
}
