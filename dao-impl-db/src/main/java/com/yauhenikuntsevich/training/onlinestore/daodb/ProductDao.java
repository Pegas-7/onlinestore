package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public interface ProductDao {
	Product get(Long id);

	void add(Product product);

	void update(Product product);

	void delete(Long id);

	List<Product> getAll();
}
