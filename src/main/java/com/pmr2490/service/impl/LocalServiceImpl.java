package com.pmr2490.service.impl;

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
	public int create(String name) throws Exception {
		Local local = new Local();
		local.setName(name);
		return this.localDao.create(local);
	}

	@Override
	@Transactional
	public void update(int id, String name) throws Exception {
		Local local = new Local();
		local.setId(id);
		local.setName(name);
		this.localDao.update(local);
	}

}
