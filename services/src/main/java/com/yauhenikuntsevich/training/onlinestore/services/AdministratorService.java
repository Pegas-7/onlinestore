package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public interface AdministratorService {

	@Transactional
	List<Administrator> saveAll(List<Administrator> administrator);

	Long save(Administrator administrator);

	Administrator get(Long id);

	List<Administrator> getAll();

	boolean delete(Long id);
}