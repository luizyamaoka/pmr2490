package com.pmr2490.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmr2490.dao.EventDao;
import com.pmr2490.dao.LocalDao;
import com.pmr2490.dao.TagDao;
import com.pmr2490.dao.UserDao;
import com.pmr2490.dto.EventDto;
import com.pmr2490.helper.Helper;
import com.pmr2490.model.Event;
import com.pmr2490.model.Local;
import com.pmr2490.model.Tagging;
import com.pmr2490.model.User;
import com.pmr2490.service.EventService;

@Component
public class EventServiceImpl implements EventService {

	private EventDao eventDao;
	private LocalDao localDao;
	private TagDao tagDao;
	private UserDao userDao;
	
	@Autowired
	public EventServiceImpl(EventDao eventDao, LocalDao locaDao, TagDao tagDao, UserDao userDao) {
		this.eventDao = eventDao;
		this.localDao = locaDao;
		this.tagDao = tagDao;
		this.userDao = userDao;
	}
	
	@Override
	@Transactional
	public List<Event> getAll() throws Exception {
		return this.eventDao.getAll();
	}

	@Override
	@Transactional
	public Event get(int id) throws Exception {
		return this.eventDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id) throws Exception {
		Event event = this.eventDao.get(id);
		this.eventDao.delete(event);
	}

	@Override
	@Transactional
	public EventDto getEventDto(int id) throws Exception {
		return this.eventDao.getEventDto(id);
	}

	@Override
	@Transactional
	public List<Event> getBySet(Integer id, String date, String name, Integer localId,
			Integer tagId) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // Create formatter
		Date data = date == null ? null : formatter.parse(date);
		return this.eventDao.getBySet(id, data, name, localId, tagId);
	}

	@Override
	@Transactional
	public Event getEager(int id) throws Exception {
		return this.eventDao.getEager(id);
	}

	@Override
	@Transactional
	public List<String> create(EventDto eventDto) throws Exception {
		
		eventDto.setPhoneNumber(Helper.removeNonDigits(eventDto.getPhoneNumber()));
		
		List<String> status = this.testEventDto(eventDto);
		if (status.size() == 1) {
			status.add(this.eventDao.create(this.EventDtoToEvent(eventDto)).toString());
		}
		return status;
	}

	@Override
	@Transactional
	public List<String> update(EventDto eventDto) throws Exception {
		
		eventDto.setPhoneNumber(Helper.removeNonDigits(eventDto.getPhoneNumber()));
		
		List<String> status = this.testEventDto(eventDto);
		if (status.size() == 1) {
			this.eventDao.update(this.EventDtoToEvent(eventDto));
			status.add(eventDto.getId().toString());
		}
		this.eventDao.update(this.EventDtoToEvent(eventDto));
		return status;
	}
	
	private List<String> testEventDto(EventDto eventDto) throws Exception {
		List<String> status = new ArrayList<String>();
		status.add("success");
		
		if(eventDto.getName() == null || eventDto.getName().equals("")) {
			status.set(0, "error");
			status.add("name.required");
		}
		if(eventDto.getEmail() == null || eventDto.getEmail().equals("")) {
			status.set(0, "error");
			status.add("email.required");
		}
		if(eventDto.getCreatorId() == null) {
			status.set(0, "error");
			status.add("creatorId.required");
		}
		if(eventDto.getLocalId() == null) {
			status.set(0, "error");
			status.add("localId.required");
		}
		if (eventDto.getMonthStart() != null && (eventDto.getMonthStart() < 1 || eventDto.getMonthStart() > 12)) {
			status.set(0, "error");
			status.add("monthStart.impossible");
		}
		if (eventDto.getDayStart() != null && (eventDto.getDayStart() < 1 || eventDto.getDayStart() > 31)) {
			status.set(0, "error");
			status.add("dayStart.impossible");
		}
		if (eventDto.getHourStart() != null && (eventDto.getHourStart() < 0 || eventDto.getHourStart() > 23)) {
			status.set(0, "error");
			status.add("hourStart.impossible");
		}
		if (eventDto.getMinuteStart() != null && (eventDto.getMinuteStart() < 0 || eventDto.getMinuteStart() > 59)) {
			status.set(0, "error");
			status.add("minuteStart.impossible");
		}
		if (eventDto.getYearStart() == null || eventDto.getMonthStart() == null || eventDto.getDayStart() == null || 
				eventDto.getHourStart() == null || eventDto.getMinuteStart() == null) {
			status.set(0, "error");
			status.add("dateStart.required");
		}
		if (eventDto.getMonthEnd() != null && (eventDto.getMonthEnd() < 1 || eventDto.getMonthEnd() > 12)) {
			status.set(0, "error");
			status.add("monthEnd.impossible");
		}
		if (eventDto.getDayEnd() != null && (eventDto.getDayEnd() < 1 || eventDto.getDayEnd() > 31)) {
			status.set(0, "error");
			status.add("dayEnd.impossible");
		}
		if (eventDto.getHourEnd() != null && (eventDto.getHourEnd() < 0 || eventDto.getHourEnd() > 23)) {
			status.set(0, "error");
			status.add("hourEnd.impossible");
		}
		if (eventDto.getMinuteEnd() != null && (eventDto.getMinuteEnd() < 0 || eventDto.getMinuteEnd() > 59)) {
			status.set(0, "error");
			status.add("minuteEnd.impossible");
		}
		if ((eventDto.getYearEnd() != null || eventDto.getMonthEnd() != null || eventDto.getDayEnd() != null || eventDto.getHourEnd() != null || eventDto.getMinuteEnd() != null) &&
				(eventDto.getYearEnd() == null || eventDto.getMonthEnd() == null || eventDto.getDayEnd() == null || eventDto.getHourEnd() == null || eventDto.getMinuteEnd() == null)) {
			status.set(0, "error");
			status.add("dateEnd.incomplete");
		}
		int phoneNumberLength = eventDto.getPhoneNumber().length();
		if (!eventDto.getPhoneNumber().equals("") && (phoneNumberLength < 8 || phoneNumberLength > 9)) {
			status.set(0, "error");
			status.add("phoneNumber.length");
		}
		if ((eventDto.getPhoneDdd() != null || !eventDto.getPhoneNumber().equals("")) &&
				(eventDto.getPhoneDdd() == null || eventDto.getPhoneNumber().equals(""))) {
			status.set(0, "error");
			status.add("phoneNumber.incomplete");
		}
		int descriptionLength = eventDto.getDescription() == null ? 0 : eventDto.getDescription().length();
		if (eventDto.getDescription() != null && descriptionLength > 255) {
			status.set(0, "error");
			status.add("description.length");
		}
		
		return status;
	}
	
	private Event EventDtoToEvent(EventDto eventDto) throws Exception {
		Event event = eventDto.getId() == null ? new Event() : this.eventDao.get(eventDto.getId());
		
		if (eventDto.getName() != null)
			event.setName(eventDto.getName());
		if (eventDto.getPhoneDdd() != null && !eventDto.getPhoneNumber().equals("")) {
			event.setPhoneDdd(eventDto.getPhoneDdd());
			event.setPhoneNumber(eventDto.getPhoneNumber());
		}
		if (eventDto.getYearStart() != null &&  eventDto.getMonthStart() != null && 
				eventDto.getDayStart() != null && eventDto.getHourStart() != null && eventDto.getMinuteStart() != null)
			event.setDateStart(Helper.buildDate(eventDto.getYearStart(), eventDto.getMonthStart(), eventDto.getDayStart(), eventDto.getHourStart(), eventDto.getMinuteStart()));
		if (eventDto.getYearEnd() != null &&  eventDto.getMonthEnd() != null && 
				eventDto.getDayEnd() != null && eventDto.getHourEnd() != null && eventDto.getMinuteEnd() != null)
			event.setDateEnd(Helper.buildDate(eventDto.getYearEnd(), eventDto.getMonthEnd(), eventDto.getDayEnd(), eventDto.getHourEnd(), eventDto.getMinuteEnd()));
		if (eventDto.getDescription() != null)
			event.setDescription(eventDto.getDescription());
		if (eventDto.getEmail() != null)
			event.setEmail(eventDto.getEmail());
		
		User creator = eventDto.getCreatorId() == null ? null : this.userDao.get(eventDto.getCreatorId());
		Local local = eventDto.getLocalId() == null ? null : this.localDao.get(eventDto.getLocalId());
		
		event.setCreator(creator);
		event.setLocal(local);
		
		if (eventDto.getTagIds() != null) {
			List<Tagging> taggings = new ArrayList<Tagging>();
			for(Integer tagId : eventDto.getTagIds())
				taggings.add(new Tagging(this.tagDao.get(tagId), event));
			
			event.setTaggings(taggings);
		}
		
		return event;
	}

}
