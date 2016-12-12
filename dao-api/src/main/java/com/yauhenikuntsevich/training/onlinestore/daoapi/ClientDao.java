package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public interface ClientDao {
	Client get(Long id);

	Long add(Client entity);

	Integer update(Client entity);

	Integer delete(Long id);

	List<Client> getAll();

	Client getOwnData(String firstName);

	Long getIdByFirstName(String firstName);

	List<Client> getAllClientBlacklisted(Boolean blacklisted);
}
