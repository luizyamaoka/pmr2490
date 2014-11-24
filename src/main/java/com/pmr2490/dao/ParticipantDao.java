package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Participant;

@Repository
public class ParticipantDao extends GenericDao<Participant, Integer>{

	@Autowired
	public ParticipantDao(SessionFactory sessionFactory) {
		super(sessionFactory, Participant.class);
	}
	
}
