package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public interface ProductService {

	@Transactional
	List<Product> saveAll(List<Product> products);

	Long save(Product product);

	Product get(Long id);

	List<Product> getAll();

	boolean delete(Long id);

	List<Product> getAllProductsWithOneCategory(Long categoryId);
}
