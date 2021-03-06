package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.OrderDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.OrderMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoDbImpl implements OrderDao {

	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Order get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM \"order\" o JOIN "
						+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM client) "
						+ "c ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id WHERE o.order_id = ?",
				new Object[] { id }, new OrderMapper());
	}

	@Override
	public Long add(Order entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"order\" (date_order, client_id, administrator_id, price_all_purchases) VALUES ( ?, ?, ?, ?)",
						new String[] { "order_id" });
				ps.setTimestamp(1, new Timestamp(entity.getDateOrder().getTime()));
				ps.setLong(2, entity.getClient().getId());
				ps.setLong(3, entity.getAdministrator().getId());
				ps.setDouble(4, entity.getPriceAllPurchases());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public Integer update(Order entity) {
		return jdbcTemplate.update(
				"update \"order\" set date_order = ?, client_id = ?, administrator_id = ?, price_all_purchases = ? where order_id = ?",
				entity.getDateOrder(), entity.getClient().getId(), entity.getAdministrator().getId(),
				entity.getPriceAllPurchases(), entity.getId());
	}

	@Override
	public Integer delete(Long id) {
		return jdbcTemplate.update("delete from \"order\" where order_id = ?", id);

	}

	@Override
	public List<Order> getAll() {
		return jdbcTemplate.query(
				"SELECT * FROM \"order\" o JOIN "
						+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM client) "
						+ "c ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id",
				new OrderMapper());
	}

	@Override
	public List<Order> getOwnOrders(String firstName) {
		return jdbcTemplate.query(
				"SELECT * FROM \"order\" WHERE client_id=(SELECT client_id FROM client WHERE first_name=?)",
				new Object[] { firstName }, new OrderMapper());
	}

	@Override
	public List<Order> getAllOrdersOneAdministrator(Long administratorId) {
		return jdbcTemplate.query("SELECT * FROM \"order\" o JOIN "
				+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM client) c "
				+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id WHERE o.administrator_id = ?",
				new Object[] { administratorId }, new OrderMapper());
	}

	@Override
	public List<Order> getAllOrdersOneClient(Long clientId) {
		return jdbcTemplate.query("SELECT * FROM \"order\" o JOIN "
				+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM client) c "
				+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id WHERE o.client_id = ?", new Object[] { clientId },
				new OrderMapper());
	}

	@Override
	public List<Order> getAllOrdersIntervalDate(Date before, Date after) {
		return jdbcTemplate.query("SELECT * FROM \"order\" o JOIN "
				+ "(SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted, password AS password_client, role AS role_client FROM client) c "
				+ "ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id WHERE o.date_order > ? AND o.date_order < ?",
				new Object[] { before, after }, new OrderMapper());
	}
}
