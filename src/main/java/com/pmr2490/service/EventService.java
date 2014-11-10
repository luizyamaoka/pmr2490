package com.pmr2490.service;

import java.util.Date;
import java.util.List;

import com.pmr2490.model.Event;
import com.pmr2490.model.Local;
import com.pmr2490.model.User;

public interface EventService {

	public List<Event> getAll();
	
	public Event get(int id);
	
	public void delete(int id);
	
	public int create(String name, Date startDate, Date endDate, int phoneDDD, 
			String phoneNumber, String description, User creator, Local local);
	
	public void update(int id, String name, Date startDate, Date endDate, int phoneDDD, 
			String phoneNumber, String description, User creator, Local local);
	
}
