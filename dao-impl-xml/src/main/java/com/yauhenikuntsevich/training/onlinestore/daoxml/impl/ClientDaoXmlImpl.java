package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.daoxml.exception.XmlFileNotFoundedException;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

@Repository
public class ClientDaoXmlImpl extends AbstractEntityDaoXml implements EntityDao<Client> {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(Client.class.getSimpleName(), Client.class);
		file = new File(basePath + "/" + Client.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	@Override
	public Client get(Long id) {
		List<Client> allClients = readCollection();

		for (Client client : allClients) {
			if (client.getId().equals(id)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public Long add(Client client) {
		List<Client> allClients = readCollection();
		Long id = getNextId(allClients);
		client.setId(id);
		allClients.add(client);
		writeCollection(allClients);
		return id;
	}

	@Override
	public void update(Client client) {
		List<Client> allClients = readCollection();

		for (Client client2 : allClients) {
			if (client2.getId().equals(client.getId())) {
				client2.setFirstName(client.getFirstName());
				client2.setLastName(client.getLastName());
				client2.setAge(client.getAge());
				client2.setBlacklisted(client.getBlacklisted());
				break;
			}
		}

		writeCollection(allClients);
	}

	@Override
	public void delete(Long id) {
		List<Client> allClients = readCollection();

		for (int i = 0; i < allClients.size(); i++) {
			if (allClients.get(i).getId().equals(id)) {
				allClients.remove(i);
				break;
			}
		}
		writeCollection(allClients);
	}

	@Override
	public List<Client> getAll() {
		return (List<Client>) readCollection();
	}

	private List<Client> readCollection() {
		return (List<Client>) xstream.fromXML(file);
	}

	private void writeCollection(List<Client> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<Client> allClients) {
		return allClients.size() == 0 ? 1l : allClients.get(allClients.size() - 1).getId() + 1;
	}
}
