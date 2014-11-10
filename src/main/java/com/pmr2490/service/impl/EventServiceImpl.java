package com.pmr2490.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.EventDao;
import com.pmr2490.model.Event;
import com.pmr2490.model.Local;
import com.pmr2490.model.User;
import com.pmr2490.service.EventService;

@Component
public class EventServiceImpl implements EventService {

	private EventDao eventDao;
	
	@Autowired
	public EventServiceImpl(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	@Override
	public List<Event> getAll() {
		return this.eventDao.getAll();
	}

	@Override
	public Event get(int id) {
		return this.eventDao.get(id);
	}

	@Override
	public void delete(int id) {
		Event event = this.eventDao.get(id);
		this.eventDao.delete(event);
	}

	@Override
	public int create(String name, Date dateStart, Date dateEnd, int phoneDdd,
			String phoneNumber, String description, User creator, Local local) {
		Event event = new Event(null, name, dateStart, dateEnd, (byte) phoneDdd, 
				phoneNumber, description, creator, local);
		return this.eventDao.create(event);
	}

	@Override
	public void update(int id, String name, Date dateStart, Date dateEnd,
			int phoneDdd, String phoneNumber, String description, User creator,
			Local local) {
		Event event = new Event(id, name, dateStart, dateEnd, (byte) phoneDdd, 
				phoneNumber, description, creator, local);
		this.eventDao.update(event);

	}

}
