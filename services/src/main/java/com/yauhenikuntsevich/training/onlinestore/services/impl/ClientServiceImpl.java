package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Inject
	private EntityDao<Client> clientDao;

	@Override
	public List<Client> saveAll(List<Client> clients) {
		List<Client> clients1 = new LinkedList<>();

		for (Client client2 : clients) {
			Long id = save(client2);
			client2.setId(id);
			clients1.add(client2);
		}

		return clients1;
	}

	@Override
	public Long save(Client client) {
		if (client.getId() == null) {
			return clientDao.add(client);
		} else {
			clientDao.update(client);
			return client.getId();
		}
	}

	@Override
	public Client get(Long id) {
		return clientDao.get(id);
	}

	@Override
	public List<Client> getAll() {
		return clientDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		clientDao.delete(id);
		return true;
	}

	@Override
	public List<Client> getAllClientBlacklisted(Boolean blacklisted) {
		List<Client> clients = clientDao.getAll();
		List<Client> clientsForReturn = new LinkedList<>();

		for (Client client : clients) {
			if (client.getBlacklisted() == blacklisted) {
				clientsForReturn.add(client);
			}
		}

		return clientsForReturn;
	}
}
