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

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.ProductMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoDbImpl implements EntityDao<Product> {
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
						"INSERT INTO \"product\" (category_id, name, price, quantity) VALUES (?, ?, ?, ?)",
						new String[] { "product_id" });
				ps.setLong(1, entity.getCategory().getId());
				ps.setString(2, entity.getName());
				ps.setDouble(3, entity.getPrice());
				ps.setInt(4, entity.getQuantity());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();

	}

	@Override
	public void update(Product entity) {
		jdbcTemplate.update(
				"update product set category_id = ?, name = ?, price = ?, quantity = ? where product_id = ?",
				entity.getCategory().getId(), entity.getName(), entity.getPrice(), entity.getQuantity(),
				entity.getId());

	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from product where product_id = ?", id);
	}

	@Override
	public List<Product> getAll() {
		return jdbcTemplate.query("SELECT * FROM product p JOIN category c ON p.category_id = c.category_id",
				new ProductMapper());
	}
}
