package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.ProductDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.ProductMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Product get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM (SELECT * FROM product p JOIN category c ON p.category_id = c.id WHERE p.id_product = ?) AS res",
				new Object[] { id }, new ProductMapper());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Product product) {
		// TODO Auto-generated method stub

	}
}
