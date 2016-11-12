package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Inject
	private EntityDao<Category> categoryDao;

	@Override
	public List<Category> saveAll(List<Category> categories) {
		List<Category> categories1 = new LinkedList<>();

		for (Category category2 : categories) {
			Long id = save(category2);
			category2.setId(id);
			categories1.add(category2);
		}

		return categories1;
	}

	@Override
	public Long save(Category category) {
		if (category.getId() == null) {
			return categoryDao.add(category);
		} else {
			categoryDao.update(category);
			return category.getId();
		}
	}

	@Override
	public Category get(Long id) {
		return categoryDao.get(id);
	}

	@Override
	public List<Category> getAll() {
		return categoryDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		categoryDao.delete(id);
		return true;
	}

}
