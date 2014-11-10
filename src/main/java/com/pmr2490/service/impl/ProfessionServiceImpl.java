package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public List<Profession> getAll() {
		return this.professionDao.getAll();
	}

	@Override
	public Profession get(int id) {
		return this.professionDao.get(id);
	}

	@Override
	public void delete(int id) {
		Profession profession = this.professionDao.get(id);
		this.professionDao.delete(profession);
	}

	@Override
	public int create(String name) {
		Profession profession = new Profession();
		profession.setName(name);
		return this.professionDao.create(profession);
	}

	@Override
	public void update(int id, String name) {
		Profession profession = new Profession();
		profession.setId(id);
		profession.setName(name);
		this.professionDao.update(profession);
	}

}
