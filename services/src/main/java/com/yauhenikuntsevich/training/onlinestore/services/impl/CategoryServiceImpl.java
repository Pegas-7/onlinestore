package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.CategoryCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheCategory;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Inject
	private EntityDao<Category> categoryDao;

	public CategoryCaching categoryCaching = ExternalizableCacheCategory.createInstanceCategoryCaching();

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
			categoryCaching.putInCache(category.getId(), category);
			return category.getId();
		}
	}

	@Override
	public Category get(Long id) {
		Category category = null;

		if (categoryCaching.getCache().get(id) != null) {
			category = categoryCaching.getCache().get(id);
		} else {
			category = categoryDao.get(id);
			categoryCaching.putInCache(id, category);
		}

		return category;
	}

	@Override
	public List<Category> getAll() {
		return categoryDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		categoryDao.delete(id);
		categoryCaching.deleteFromCache(id);
		return true;
	}

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheCategory.writeCacheInFile(categoryCaching);
	}
}
