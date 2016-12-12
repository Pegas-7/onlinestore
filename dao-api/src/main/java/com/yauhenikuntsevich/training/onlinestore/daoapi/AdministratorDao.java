package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public interface AdministratorDao {
	Administrator get(Long id);

	Long add(Administrator entity);

	Integer update(Administrator entity);

	Integer delete(Long id);

	List<Administrator> getAll();
}
