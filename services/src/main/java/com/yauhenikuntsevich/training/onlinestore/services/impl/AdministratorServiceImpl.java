package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;

public class AdministratorServiceImpl implements AdministratorService {
	
	@Inject
	private EntityDao<Administrator> administratorDao;

	@Override
	public void saveAll(List<Administrator> administrator) {
		

	}

	@Override
	public Long save(Administrator administrator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Administrator get(Long id) {
		return administratorDao.get(id);
	}

	@Override
	public List<Administrator> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
