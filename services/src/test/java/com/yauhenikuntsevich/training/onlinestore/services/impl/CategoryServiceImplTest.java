package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	@Inject
	private EntityDao<Category> categoryDao;

	@Override
	public void saveAll(List<Category> category) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long save(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
