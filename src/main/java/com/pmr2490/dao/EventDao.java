package com.pmr2490.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.model.Event;

@Repository
public class EventDao extends GenericDao<Event, Integer> {

	@Autowired
	public EventDao(SessionFactory sessionFactory) {
		super(sessionFactory, Event.class);
	}
	
}
