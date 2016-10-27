package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.CategoryMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@Repository
public class CategoryDaoImpl implements EntityDao<Category> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Category get(Long id) {
		return jdbcTemplate.queryForObject("select * from category where category_id = ?", new Object[] { id },
				new CategoryMapper());
	}

	@Override
	public void add(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Category entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Category> getAll() {
		return jdbcTemplate.query("select * from category", new CategoryMapper());
	}
}
