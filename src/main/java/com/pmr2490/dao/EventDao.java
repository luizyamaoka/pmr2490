package com.pmr2490.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pmr2490.dto.EventDto;
import com.pmr2490.model.Event;
import com.pmr2490.model.Participant;
import com.pmr2490.model.Tagging;

@Repository
public class EventDao extends GenericDao<Event, Integer> {

	@Autowired
	public EventDao(SessionFactory sessionFactory) {
		super(sessionFactory, Event.class);
	}

	public Event getEager(int id) throws Exception {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Event event = (Event)session.get(Event.class, id);
			for(Tagging tagging : event.getTaggings())
				tagging.getTag().getName();
			for(Participant participant : event.getParticipants())
				participant.getUser().getFirstName();
			transaction.commit();
			return event;
			
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

	@SuppressWarnings("unchecked")
	public List<Event> getBySet(Date date, String name, Integer localId, Integer tagId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Event.class);
			criteria.createAlias("local", "l");
			criteria.createAlias("taggings", "tt", JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("tt.tag", "t", JoinType.LEFT_OUTER_JOIN);
			
			if(date != null) {
				Date dateStart = getDateWithoutTime(date);
				Date dateEnd = getTomorrowDate(dateStart);
				criteria.add(Restrictions.ge("dateStart", dateStart)); 
				criteria.add(Restrictions.lt("dateStart", dateEnd));
			}
			if(name != null) 
				criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			if(localId != null)
				criteria.add(Restrictions.eq("l.id", localId));
			if(tagId != null)
				criteria.add(Restrictions.eq("t.id", tagId));
			
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(Order.asc("dateStart"));
			
			List<Event> events = criteria.list();
			transaction.commit();
			return events;
			
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
	
	private Date getDateWithoutTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}

	private Date getTomorrowDate(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, 1);
	    return cal.getTime();
	}
	
}
