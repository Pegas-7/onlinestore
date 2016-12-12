package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.ProductDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.ProductMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoDbImpl implements ProductDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Product get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM product p JOIN category c ON p.category_id = c.category_id WHERE p.product_id = ?",
				new Object[] { id }, new ProductMapper());
	}

	@Override
	public Long add(Product entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"product\" (category_id, name, price, quantity_store) VALUES (?, ?, ?, ?)",
						new String[] { "product_id" });
				ps.setLong(1, entity.getCategory().getId());
				ps.setString(2, entity.getName());
				ps.setDouble(3, entity.getPrice());
				ps.setInt(4, entity.getQuantityStore());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public Integer update(Product entity) {
		return jdbcTemplate.update(
				"update product set category_id = ?, name = ?, price = ?, quantity_store = ? where product_id = ?",
				entity.getCategory().getId(), entity.getName(), entity.getPrice(), entity.getQuantityStore(),
				entity.getId());
	}

	@Override
	public Integer delete(Long id) {
		return jdbcTemplate.update("delete from product where product_id = ?", id);
	}

	@Override
	public List<Product> getAll() {
		return jdbcTemplate.query("SELECT * FROM product p JOIN category c ON p.category_id = c.category_id",
				new ProductMapper());
	}

	@Override
	public List<Product> getAllProductsWithOneCategory(Long categoryId) {
		return jdbcTemplate.query(
				"SELECT * FROM product p JOIN category c ON p.category_id = c.category_id WHERE c.category_id = ?",
				new Object[] { categoryId }, new ProductMapper());
	}
}
