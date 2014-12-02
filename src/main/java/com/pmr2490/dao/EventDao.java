package com.pmr2490.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		session = this.sessionFactory.getCurrentSession();
		Event event = (Event)session.get(Event.class, id);
		for(Tagging tagging : event.getTaggings())
			tagging.getTag().getName();
		for(Participant participant : event.getParticipants())
			participant.getUser().getFirstName();
		return event;
	}
	
	public EventDto getEventDto(int id) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		Event event = (Event)session.get(Event.class, id);
		EventDto eventDto = event.toEventDto();
		return eventDto;
	}
	
	public void approve(int id) {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		Event event = (Event)session.get(Event.class, id);
		event.setApproved(true);
	}

	@SuppressWarnings("unchecked")
	public List<Event> getBySet(Integer id, Date date, String name, Integer localId, 
			Integer tagId, Boolean isApproved, Integer max) throws Exception {
		Session session = null;
		session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("local", "l");
		criteria.createAlias("taggings", "tt", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("tt.tag", "t", JoinType.LEFT_OUTER_JOIN);
		
		if (id != null) 
			criteria.add(Restrictions.eq("id", id));
		if(date != null) {
			Date dateStart = getDateWithoutTime(date);
			Date dateEnd = getTomorrowDate(dateStart);
			criteria.add(Restrictions.ge("dateStart", dateStart)); 
			criteria.add(Restrictions.lt("dateStart", dateEnd));
		} else {
			criteria.add(Restrictions.ge("dateStart", Calendar.getInstance().getTime()));
		}
		if(name != null) 
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		if(localId != null)
			criteria.add(Restrictions.eq("l.id", localId));
		if(tagId != null)
			criteria.add(Restrictions.eq("t.id", tagId));
		if(isApproved != null)
			criteria.add(Restrictions.eq("isApproved", isApproved));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("dateStart"));
		if (max != null) criteria.setMaxResults(max);	
		
		List<Event> events = criteria.list();
		return events;
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
