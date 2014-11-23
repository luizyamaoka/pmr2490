package com.pmr2490.service;

import java.util.Date;
import java.util.List;

import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;
import com.pmr2490.model.Local;
import com.pmr2490.model.Tag;
import com.pmr2490.model.User;

public interface EventService {

	public List<Event> getAll() throws Exception;
	
	public List<Event> getBySet(String date, String name, Integer localId, Integer tagId) throws Exception;
	
	public Event get(int id) throws Exception;
	
	public EventDto getEventDto(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public int create(String name, Date startDate, Date endDate, String email, Integer phoneDDD, 
			String phoneNumber, String description, User creator, Local local, List<Tag> tags) throws Exception;
	
	public void update(int id, String name, Date startDate, Date endDate, String email, Integer phoneDDD, 
			String phoneNumber, String description, Local local, List<Tag> tags) throws Exception;
	
	
}
