package com.pmr2490.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pmr2490.dao.LocalDao;
import com.pmr2490.model.Local;
import com.pmr2490.service.LocalService;

@Component
public class LocalServiceImpl implements LocalService {

	private LocalDao localDao;
	
	@Autowired
	public LocalServiceImpl(LocalDao localDao) {
		this.localDao = localDao;
	}
	
	@Override
	@Transactional
	public List<Local> getAll() throws Exception {
		return this.localDao.getAll();
	}

	@Override
	@Transactional
	public Local get(int id) throws Exception {
		return this.localDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id) throws Exception {
		Local local = this.localDao.get(id);
		this.localDao.delete(local);
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
		if (this.localDao.getByName(name) != null) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Local local = new Local();
			local.setName(name);
			status.add(this.localDao.create(local).toString());
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
		Local existantLocal = this.localDao.getByName(name);
		if (existantLocal != null && existantLocal.getId() != id) {
			status.set(0, "error");
			status.add("name.existant");
		}
		if (status.size() == 1) {
			Local local = this.localDao.get(id);
			local.setName(name);
			this.localDao.update(local);
			status.add(String.valueOf(id));
		}
		
		return status;
	}

}
