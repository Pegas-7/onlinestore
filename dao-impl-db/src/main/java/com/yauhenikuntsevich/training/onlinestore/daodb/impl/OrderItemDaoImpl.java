package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.AdministratorMapper;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.OrderItemMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

@Repository
public class OrderItemDaoImpl implements EntityDao<OrderItem> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public OrderItem get(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM \"order_item\" oi JOIN (SELECT * FROM \"order\" o "
				+ "JOIN (SELECT client_id, first_name AS first_name_client, last_name AS last_name_client, age, blacklisted FROM \"client\") c "
				+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id) oca ON oca.order_id = oi.order_id "
				+ "JOIN(SELECT * FROM product p JOIN category c ON p.category_id = c.category_id) pc ON pc.product_id = oi.product_id "
				+ "WHERE oi.order_item_id = ?", new Object[] { id }, new OrderItemMapper());
	}

	@Override
	public void add(OrderItem entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OrderItem entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderItem> getAll() {
		return jdbcTemplate.query(
				"SELECT * FROM \"order_item\" oi JOIN (SELECT * FROM \"order\" o "
						+ "JOIN (SELECT client_id, first_name AS first_name_client, last_name AS last_name_client, age, blacklisted FROM \"client\") c "
						+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id) oca ON oca.order_id = oi.order_id "
						+ "JOIN(SELECT * FROM product p JOIN category c ON p.category_id = c.category_id) pc ON pc.product_id = oi.product_id",
				new OrderItemMapper());
	}
}
