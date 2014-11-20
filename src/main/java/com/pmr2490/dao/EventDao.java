package com.pmr2490.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;

@Repository
public class EventDao extends GenericDao<Event, Integer> {

	@Autowired
	public EventDao(SessionFactory sessionFactory) {
		super(sessionFactory, Event.class);
	}

	public EventDto getEventDto(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Event event = (Event)session.get(Event.class, id);
			EventDto eventDto = event.toEventDto();
			transaction.commit();
			return eventDto;
			
		}
		catch(Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw new Exception();
		}
		finally {
			if (session.isOpen())
				session.close();
		}
	}
	
}
