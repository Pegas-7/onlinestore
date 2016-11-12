package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.sql.Connection;
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

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.OrderMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoDbImpl implements EntityDao<Order> {

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
				ps.setInt(4, entity.getPriceAllPurchases());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void update(Order entity) {
		jdbcTemplate.update(
				"update \"order\" set date_order = ?, client_id = ?, administrator_id = ?, price_all_purchases = ? where order_id = ?",
				entity.getDateOrder(), entity.getClient().getId(), entity.getAdministrator().getId(),
				entity.getPriceAllPurchases(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from \"order\" where order_id = ?", id);

	}

	@Override
	public List<Order> getAll() {
		return jdbcTemplate.query(
				"SELECT * FROM \"order\" o JOIN (SELECT client_id,first_name AS first_name_client, last_name AS last_name_client, age, blacklisted FROM client) "
						+ "c ON o.client_id = c.client_id JOIN administrator a ON o.administrator_id = a.administrator_id",
				new OrderMapper());
	}
}
