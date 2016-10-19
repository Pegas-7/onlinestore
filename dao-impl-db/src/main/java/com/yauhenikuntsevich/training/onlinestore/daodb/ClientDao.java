package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public interface ClientDao {
	Client get(Long id);

	void save(Client entity);

	void update(Client entity);

	void delete(Long id);

	List<Client> getAll();
}
