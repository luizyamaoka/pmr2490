package com.pmr2490.service.impl;

import java.util.ArrayList;
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
		if (this.professionDao.getByName(name) != null) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Profession profession = new Profession();
			profession.setName(name);
			status.add(this.professionDao.create(profession).toString());
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
		Profession existantProfession = this.professionDao.getByName(name);
		if (existantProfession != null && existantProfession.getId() != id) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Profession profession = this.professionDao.get(id);
			profession.setName(name);
			this.professionDao.update(profession);
			status.add(String.valueOf(id));
		}
		
		return status;
	}

}
