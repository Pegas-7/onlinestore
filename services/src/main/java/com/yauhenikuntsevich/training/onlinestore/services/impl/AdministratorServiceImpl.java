package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.AdministratorCaching;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Inject
	private EntityDao<Administrator> administratorDao;

	@Override
	public List<Administrator> saveAll(List<Administrator> administrators) {
		List<Administrator> administrators1 = new LinkedList<>();

		for (Administrator administrator2 : administrators) {
			Long id = save(administrator2);
			administrator2.setId(id);
			administrators1.add(administrator2);
		}

		return administrators1;
	}

	@Override
	public Long save(Administrator administrator) {
		if (administrator.getId() == null) {
			return administratorDao.add(administrator);
		} else {
			administratorDao.update(administrator);
			return administrator.getId();
		}
	}

	@Override
	public Administrator get(Long id) {
		Administrator administrator = null;

		if (AdministratorCaching.getCache().get(id) != null) {
			administrator = AdministratorCaching.getCache().get(id);
		} else {
			administrator = administratorDao.get(id);
			AdministratorCaching.putEntityInCache(id, administrator);
		}

		return administrator;
	}

	@Override
	public List<Administrator> getAll() {
		return administratorDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		administratorDao.delete(id);
		return true;
	}
}
