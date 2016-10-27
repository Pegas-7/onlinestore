package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.AdministratorMapper;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.ProductMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoImpl implements EntityDao<Product> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Product get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM product p JOIN category c ON p.category_id = c.category_id WHERE p.product_id = ?",
				new Object[] { id }, new ProductMapper());
	}

	@Override
	public void add(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> getAll() {
		return jdbcTemplate.query("SELECT * FROM product p JOIN category c ON p.category_id = c.category_id",
				new ProductMapper());
	}
}
