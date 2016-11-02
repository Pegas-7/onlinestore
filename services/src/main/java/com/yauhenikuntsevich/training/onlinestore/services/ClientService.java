package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public interface ClientService {

	@Transactional
	List<Client> saveAll(List<Client> client);

	Long save(Client client);

	Client get(Long id);

	List<Client> getAll();

	boolean delete(Long id);

	List<Client> getAllClientBlacklisted(Boolean blacklisted);
}
