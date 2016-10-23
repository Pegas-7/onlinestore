package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public interface CategoryDao {
	
	Category get(Long id);

	void addCategory(Category category);

	void delete(Long id);

	List<Category> getAll();
}
