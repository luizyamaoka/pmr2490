package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public List<College> getAll() {
		return this.collegeDao.getAll();
	}

	@Override
	public College get(int id) {
		return this.collegeDao.get(id);
	}

	@Override
	public void delete(int id) {
		College college = this.collegeDao.get(id);
		this.collegeDao.delete(college);

	}

	@Override
	public int create(String name) {
		College college = new College();
		college.setName(name);
		return this.collegeDao.create(college);
	}

	@Override
	public void update(int id, String name) {
		College college = new College();
		college.setId(id);
		college.setName(name);
		this.collegeDao.update(college);
	}

}
