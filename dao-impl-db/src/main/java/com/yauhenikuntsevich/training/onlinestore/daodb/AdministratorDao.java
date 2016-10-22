package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public interface AdministratorDao {
	Administrator get(Long id);

	Integer add(Administrator administrator);

	void update(Administrator administrator);

	void delete(Long id);

	List<Administrator> getAll();
}
