package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public interface ProductDao {
	Product get(Long id);

	Long add(Product entity);

	Integer update(Product entity);

	Integer delete(Long id);

	List<Product> getAll();

	List<Product> getAllProductsWithOneCategory(Long categoryId);
}
