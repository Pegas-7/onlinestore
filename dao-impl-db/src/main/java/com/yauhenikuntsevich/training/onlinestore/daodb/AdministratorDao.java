package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public interface AdministratorDao {
	Administrator get(Long id);

	void save(Administrator entity);

	void update(Administrator entity);

	void delete(Long id);

	List<Administrator> getAll();
}
