package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

@Repository
public class ClientDaoXmlImpl implements EntityDao<Client> {

	@Override
	public Client get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long add(Client entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Client entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Client> getAll() {
		throw new UnsupportedOperationException();
	}
}
