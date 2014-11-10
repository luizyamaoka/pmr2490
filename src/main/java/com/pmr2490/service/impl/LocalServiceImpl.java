package com.pmr2490.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public List<Local> getAll() {
		return this.localDao.getAll();
	}

	@Override
	public Local get(int id) {
		return this.localDao.get(id);
	}

	@Override
	public void delete(int id) {
		Local local = this.localDao.get(id);
		this.localDao.delete(local);
	}

	@Override
	public int create(String name) {
		Local local = new Local();
		local.setName(name);
		return this.localDao.create(local);
	}

	@Override
	public void update(int id, String name) {
		Local local = new Local();
		local.setId(id);
		local.setName(name);
		this.localDao.update(local);
	}

}
