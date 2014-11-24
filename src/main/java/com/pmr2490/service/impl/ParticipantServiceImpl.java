package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.ParticipantDao;
import com.pmr2490.model.Event;
import com.pmr2490.model.Participant;
import com.pmr2490.model.User;
import com.pmr2490.service.ParticipantService;

@Component
public class ParticipantServiceImpl implements ParticipantService {

	private ParticipantDao participantDao;
	
	@Autowired
	public ParticipantServiceImpl(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	@Override
	public List<Participant> getAll() throws Exception {
		return this.participantDao.getAll();
	}

	@Override
	public Participant get(int id) throws Exception {
		return this.participantDao.get(id);
	}

	@Override
	public void delete(int id) throws Exception {
		Participant participant = this.participantDao.get(id);
		this.participantDao.delete(participant);
		
	}

	@Override
	public int create(Event event, User user) throws Exception {
		Participant participant = new Participant();
		participant.setEvent(event);
		participant.setUser(user);
		return this.participantDao.create(participant);
	}

	@Override
	public void update(int id, Event event, User user)
			throws Exception {
		Participant participant = this.participantDao.get(id);
		participant.setEvent(event);
		participant.setUser(user);
		this.participantDao.update(participant);
	}

}
