package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public interface CategoryService {
	
	@Transactional
	void saveAll(List<Category> category);

	Long save(Category category);

	Category get(Long id);

	List<Category> getAll();

	boolean delete(Long id);
}
