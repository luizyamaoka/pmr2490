package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmr2490.dao.TagDao;
import com.pmr2490.model.Tag;
import com.pmr2490.service.TagService;

@Component
public class TagServiceImpl implements TagService {

	private TagDao tagDao;
	
	@Autowired
	public TagServiceImpl(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	
	@Override
	public List<Tag> getAll() throws Exception {
		return this.tagDao.getAll();
	}

	@Override
	public Tag get(int id) throws Exception {
		return this.tagDao.get(id);
	}

	@Override
	public void delete(int id) throws Exception {
		Tag tag = this.tagDao.get(id);
		this.tagDao.delete(tag);
	}

	@Override
	public int create(String name) throws Exception {
		Tag tag = new Tag();
		tag.setName(name);
		return this.tagDao.create(tag);
	}

	@Override
	public void update(int id, String name) throws Exception {
		Tag tag = new Tag();
		tag.setId(id);
		tag.setName(name);
		this.tagDao.update(tag);
	}

}
