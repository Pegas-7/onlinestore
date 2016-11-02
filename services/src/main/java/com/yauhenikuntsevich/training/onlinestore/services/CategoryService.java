package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public interface CategoryService {
	
	@Transactional
	List<Category> saveAll(List<Category> categories);

	Long save(Category category);

	Category get(Long id);

	List<Category> getAll();

	boolean delete(Long id);
}
