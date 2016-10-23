package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.CategoryDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Category get(Long id) {
		return jdbcTemplate.queryForObject("select * from category where id = ?", new Object[] { id },
				new BeanPropertyRowMapper<Category>(Category.class));
	}

	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
