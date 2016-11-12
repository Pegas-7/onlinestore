package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private EntityDao<Product> productDao;

	@Override
	public List<Product> saveAll(List<Product> products) {
		List<Product> products1 = new LinkedList<>();

		for (Product product2 : products) {
			Long id = save(product2);
			product2.setId(id);
			products1.add(product2);
		}

		return products1;
	}

	@Override
	public Long save(Product product) {
		if (product.getId() == null) {
			return productDao.add(product);
		} else {
			productDao.update(product);
			return product.getId();
		}
	}

	@Override
	public Product get(Long id) {
		return productDao.get(id);
	}

	@Override
	public List<Product> getAll() {
		return productDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		productDao.delete(id);
		return true;
	}

	@Override
	public List<Product> getAllProductsWithOneCategory(Long categoryId) {
		List<Product> products = productDao.getAll();
		List<Product> productsForReturn = new LinkedList<>();

		for (Product product : products) {
			if (product.getCategory().getId() == categoryId) {
				productsForReturn.add(product);
			}
		}

		return productsForReturn;
	}
}
