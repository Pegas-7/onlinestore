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
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.OrderItemMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

@Repository
public class OrderItemDaoDbImpl implements EntityDao<OrderItem> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public OrderItem get(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM \"order_item\" oi JOIN (SELECT * FROM \"order\" o "
				+ "JOIN (SELECT client_id, first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM \"client\") c "
				+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id) oca ON oca.order_id = oi.order_id "
				+ "JOIN(SELECT * FROM product p JOIN category c ON p.category_id = c.category_id) pc ON pc.product_id = oi.product_id "
				+ "WHERE oi.order_item_id = ?", new Object[] { id }, new OrderItemMapper());
	}

	@Override
	public Long add(OrderItem entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"order_item\" (order_id, product_id, quantity) VALUES (?, ?, ?)",
						new String[] { "order_item_id" });
				ps.setLong(1, entity.getOrder().getId());
				ps.setLong(2, entity.getProduct().getId());
				ps.setInt(3, entity.getQuantity());

				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void update(OrderItem entity) {
		jdbcTemplate.update("update order_item set order_id = ?, product_id = ?, quantity = ? where order_item_id = ?",
				entity.getOrder().getId(), entity.getProduct().getId(), entity.getQuantity(), entity.getId());

	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from \"order_item\" where order_item_id = ?", id);
	}

	@Override
	public List<OrderItem> getAll() {
		return jdbcTemplate.query(
				"SELECT * FROM \"order_item\" oi JOIN (SELECT * FROM \"order\" o "
						+ "JOIN (SELECT client_id, first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM \"client\") c "
						+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id) oca ON oca.order_id = oi.order_id "
						+ "JOIN(SELECT * FROM product p JOIN category c ON p.category_id = c.category_id) pc ON pc.product_id = oi.product_id",
				new OrderItemMapper());
	}
}
