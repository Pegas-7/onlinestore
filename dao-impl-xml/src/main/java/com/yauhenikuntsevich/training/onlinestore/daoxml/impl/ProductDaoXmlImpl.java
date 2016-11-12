package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoXmlImpl implements EntityDao<Product> {

	@Override
	public Product get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long add(Product entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Product entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Product> getAll() {
		throw new UnsupportedOperationException();
	}
}
