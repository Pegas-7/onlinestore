package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.AdministratorMapper;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.OrderMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoImpl implements EntityDao<Order> {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Order get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM \"order\" o JOIN "
						+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted FROM client) c "
						+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id WHERE o.order_id = ?",
				new Object[] { id }, new OrderMapper());
	}

	@Override
	public void add(Order entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Order entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> getAll() {
		return jdbcTemplate.query(
				"SELECT * FROM \"order\" o JOIN (SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted FROM client) "
						+ "c ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id",
				new OrderMapper());
	}
}
