package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.AdministratorCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheAdministrator;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Inject
	private EntityDao<Administrator> administratorDao;

	private AdministratorCaching administratorCaching = ExternalizableCacheAdministrator
			.createInstanceAdministratorCaching();

	public AdministratorCaching getAdministratorCaching() {
		return administratorCaching;
	}

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
			administratorCaching.updateAdministratorInCache(administrator.getId(), administrator);
			return administrator.getId();
		}
	}

	@Override
	public Administrator get(Long id) {
		Administrator administrator = null;

		if (administratorCaching.getCache().get(id) != null) {
			administrator = administratorCaching.getCache().get(id);
		} else {
			administrator = administratorDao.get(id);
			administratorCaching.putAdministratorInCache(id, administrator);
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
		administratorCaching.deleteAdministratorFromCache(id);
		return true;
	}
}
