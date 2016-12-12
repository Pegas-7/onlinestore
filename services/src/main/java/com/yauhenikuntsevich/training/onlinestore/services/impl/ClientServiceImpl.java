package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.ClientDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.ClientCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheClient;

@Service
public class ClientServiceImpl implements ClientService {

	@Inject
	private ClientDao clientDao;

	public static ClientCaching clientCaching = ExternalizableCacheClient.createInstanceClientCaching();

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
			Integer rows = clientDao.update(client);
			if (rows > 0) {
				clientCaching.putInCache(client.getId(), client);
				return client.getId();
			}
			return -1L;
		}
	}

	@Override
	public Client get(Long id) {
		Client client = null;

		if (clientCaching.getCache().get(id) != null) {
			client = clientCaching.getCache().get(id);
		} else {
			client = clientDao.get(id);
			clientCaching.putInCache(id, client);
		}

		return client;
	}

	@Override
	public List<Client> getAll() {
		return clientDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		Integer rows = clientDao.delete(id);
		if (rows > 0) {
			clientCaching.deleteFromCache(id);
			return true;
		}
		return false;
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

	@Override
	public Client getOwnData(String firstName) {
		List<Client> clients = clientDao.getAll();

		for (Client client : clients) {
			if (client.getFirstName().equals(firstName)) {
				return client;
			}
		}

		return null;
	}

	@Override
	public Long getIdByFirstName(String firstName) {
		List<Client> clients = clientDao.getAll();

		for (Client client : clients) {
			if (client.getFirstName().equals(firstName)) {
				return client.getId();
			}
		}

		return null;
	}

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheClient.writeCacheInFile(clientCaching);
	}
}
