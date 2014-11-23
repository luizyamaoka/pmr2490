package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.TaggingDao;
import com.pmr2490.model.Event;
import com.pmr2490.model.Tag;
import com.pmr2490.model.Tagging;
import com.pmr2490.service.TaggingService;

@Component
public class TaggingServiceImpl implements TaggingService {

	private TaggingDao taggingDao;
	
	@Autowired
	public TaggingServiceImpl(TaggingDao taggingDao) {
		this.taggingDao = taggingDao;
	}
	
	@Override
	public List<Tagging> getAll() throws Exception {
		return this.taggingDao.getAll();
	}

	@Override
	public Tagging get(int id) throws Exception {
		return this.taggingDao.get(id);
	}

	@Override
	public void delete(int id) throws Exception {
		Tagging tagging = this.taggingDao.get(id);
		this.taggingDao.delete(tagging);
	}

	@Override
	public int create(Event event, Tag tag) throws Exception {
		Tagging tagging = new Tagging(tag, event);
		return this.taggingDao.create(tagging);
	}

	@Override
	public void update(int id, Event event, Tag tag) throws Exception {
		Tagging tagging = this.taggingDao.get(id);
		tagging.setEvent(event);
		tagging.setTag(tag);
		this.taggingDao.update(tagging);
	}

}
