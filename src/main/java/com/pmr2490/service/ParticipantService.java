package com.pmr2490.service;

import java.util.List;

import com.pmr2490.model.Event;
import com.pmr2490.model.Participant;
import com.pmr2490.model.User;

public interface ParticipantService {

	public List<Participant> getAll() throws Exception;
	
	public Participant get(int id) throws Exception;
	
	public void delete(int id) throws Exception;
	
	public int create(Event event, User user) throws Exception;
	
	public void update(int id, Event event, User user) throws Exception;
	
}
