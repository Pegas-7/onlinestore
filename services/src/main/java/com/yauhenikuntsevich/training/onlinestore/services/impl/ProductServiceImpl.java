package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.ProductService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.ProductCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheProduct;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private EntityDao<Product> productDao;

	public ProductCaching productCaching = ExternalizableCacheProduct.createInstanceProductCaching();

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
			Integer rows = productDao.update(product);
			if (rows > 0) {
				productCaching.putInCache(product.getId(), product);
				return product.getId();
			}
			return -1L;
		}
	}

	@Override
	public Product get(Long id) {
		Product product = null;

		if (productCaching.getCache().get(id) != null) {
			product = productCaching.getCache().get(id);
		} else {
			product = productDao.get(id);
			productCaching.putInCache(id, product);
		}

		return product;
	}

	@Override
	public List<Product> getAll() {
		return productDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		Integer rows = productDao.delete(id);
		if (rows > 0) {
			productCaching.deleteFromCache(id);
			return true;
		}
		return false;
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

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheProduct.writeCacheInFile(productCaching);
	}
}
