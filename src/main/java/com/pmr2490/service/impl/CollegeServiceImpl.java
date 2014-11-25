package com.pmr2490.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmr2490.dao.CollegeDao;
import com.pmr2490.model.College;
import com.pmr2490.service.CollegeService;

@Component
public class CollegeServiceImpl implements CollegeService {

	private CollegeDao collegeDao;
	
	@Autowired
	public CollegeServiceImpl(CollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}
	
	@Override
	@Transactional
	public List<College> getAll() throws Exception {
		return this.collegeDao.getAll();
	}

	@Override
	@Transactional
	public College get(int id) throws Exception {
		return this.collegeDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id) throws Exception {
		College college = this.collegeDao.get(id);
		this.collegeDao.delete(college);
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
		if (name.length() > 50) {
			status.set(0, "error");
			status.add("name.toolong");
		}
		if (this.collegeDao.getByName(name) != null) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			College college = new College();
			college.setName(name);
			status.add(this.collegeDao.create(college).toString());
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
		College existantCollege = this.collegeDao.getByName(name);
		if (existantCollege != null && existantCollege.getId() != id) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			College college = this.collegeDao.get(id);
			college.setName(name);
			this.collegeDao.update(college);
			status.add(String.valueOf(id));
		}
		
		return status;
	}

}
