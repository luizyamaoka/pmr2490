package com.pmr2490.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public List<Tag> getAll() throws Exception {
		return this.tagDao.getAll();
	}

	@Override
	@Transactional
	public Tag get(int id) throws Exception {
		return this.tagDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id) throws Exception {
		Tag tag = this.tagDao.get(id);
		this.tagDao.delete(tag);
	}

	@Override
	@Transactional
	public List<String> create(String name) throws Exception {
		
		List<String> status = new ArrayList<String>();
		status.add("success");

		if (name.isEmpty()) {
			status.set(0, "error");
			status.add("name.required");
		}
		if (name.length() > 20) {
			status.set(0, "error");
			status.add("name.toolong");
		}
		if (this.tagDao.getByName(name) != null) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Tag tag = new Tag();
			tag.setName(name);
			status.add(this.tagDao.create(tag).toString());
		}
		return status;
	}

	@Override
	@Transactional
	public List<String> update(int id, String name) throws Exception {
		
		List<String> status = new ArrayList<String>();
		status.add("success");

		if (name.isEmpty()) {
			status.set(0, "error");
			status.add("name.required");
		}
		if (name.length() > 50) {
			status.set(0, "error");
			status.add("name.toolong");
		}
		Tag existantTag = this.tagDao.getByName(name);
		if (existantTag != null && existantTag.getId() != id) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Tag tag = this.tagDao.get(id);
			tag.setName(name);
			this.tagDao.update(tag);
			status.add(String.valueOf(id));
		}
		
		return status;
	}
}
