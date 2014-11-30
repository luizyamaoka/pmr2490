package com.pmr2490.service;

import java.util.List;

import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;

public interface EventService {

	public List<Event> getAll() throws Exception;
	
	public List<Event> getBySet(Integer id, String date, String name, Integer localId, Integer tagId) throws Exception;
	
	public Event get(int id) throws Exception;
	
	public Event getEager(int id) throws Exception;
	
	public EventDto getEventDto(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public List<String> create(EventDto eventDto) throws Exception;
	
	public List<String> update(EventDto eventDto) throws Exception;
	
}
