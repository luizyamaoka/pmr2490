package com.pmr2490.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.EventDao;
import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;
import com.pmr2490.model.Local;
import com.pmr2490.model.Tag;
import com.pmr2490.model.Tagging;
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
	public List<Event> getAll() throws Exception {
		return this.eventDao.getAll();
	}

	@Override
	public Event get(int id) throws Exception {
		return this.eventDao.get(id);
	}

	@Override
	public void delete(int id) throws Exception {
		Event event = this.eventDao.get(id);
		this.eventDao.delete(event);
	}

	@Override
	public int create(String name, Date dateStart, Date dateEnd, String email, Integer phoneDdd,
			String phoneNumber, String description, User creator, Local local, List<Tag> tags) throws Exception {
		Event event = new Event(null, name, dateStart, dateEnd,  email, phoneDdd, 
				phoneNumber, description, creator, local);
		
		for(Tag tag : tags)
			event.getTaggings().add(new Tagging(tag, event));
		
		return this.eventDao.create(event);
	}

	@Override
	public void update(int id, String name, Date dateStart, Date dateEnd, String email, 
			Integer phoneDdd, String phoneNumber, String description, Local local, List<Tag> tags) throws Exception {
		Event event = this.eventDao.get(id);
		event.setName(name);
		event.setDateStart(dateStart);
		event.setDateEnd(dateEnd);
		event.setEmail(email);
		event.setPhoneDdd(phoneDdd);
		event.setPhoneNumber(phoneNumber);
		event.setDescription(description);
		event.setLocal(local);
		
		List<Tagging> taggings = new ArrayList<Tagging>();
		for(Tag tag : tags)
			taggings.add(new Tagging(tag, event));
		
		event.setTaggings(taggings);
		
		this.eventDao.update(event);

	}

	@Override
	public EventDto getEventDto(int id) throws Exception {
		return this.eventDao.getEventDto(id);
	}

	@Override
	public List<Event> getBySet(String date, String name, Integer localId,
			Integer tagId) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // Create formatter
		Date data = date == null ? null : formatter.parse(date);
		return this.eventDao.getBySet(data, name, localId, tagId);
	}

}
