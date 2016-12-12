package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public interface CategoryDao {
	Category get(Long id);

	Long add(Category entity);

	Integer update(Category entity);

	Integer delete(Long id);

	List<Category> getAll();
}
