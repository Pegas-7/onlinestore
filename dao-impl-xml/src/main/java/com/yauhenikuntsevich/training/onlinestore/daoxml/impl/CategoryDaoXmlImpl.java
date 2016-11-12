package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@Repository
public class CategoryDaoXmlImpl implements EntityDao<Category> {

	@Override
	public Category get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long add(Category entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Category entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Category> getAll() {
		throw new UnsupportedOperationException();
	}
}
