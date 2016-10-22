package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public interface ClientDao {
	Client get(Long id);

	Integer add(Client client);

	void update(Client client);

	void delete(Long id);

	List<Client> getAll();
}
