package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmr2490.dao.ProfessionDao;
import com.pmr2490.model.Profession;
import com.pmr2490.service.ProfessionService;

@Component
public class ProfessionServiceImpl implements ProfessionService {

	private ProfessionDao professionDao;
	
	@Autowired
	public ProfessionServiceImpl(ProfessionDao professionDao) {
		this.professionDao = professionDao;
	}
	
	@Override
	@Transactional
	public List<Profession> getAll() throws Exception {
		return this.professionDao.getAll();
	}

	@Override
	@Transactional
	public Profession get(int id) throws Exception {
		return this.professionDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id) throws Exception {
		Profession profession = this.professionDao.get(id);
		this.professionDao.delete(profession);
	}

	@Override
	@Transactional
	public int create(String name) throws Exception {
		Profession profession = new Profession();
		profession.setName(name);
		return this.professionDao.create(profession);
	}

	@Override
	@Transactional
	public void update(int id, String name) throws Exception {
		Profession profession = new Profession();
		profession.setId(id);
		profession.setName(name);
		this.professionDao.update(profession);
	}

}
