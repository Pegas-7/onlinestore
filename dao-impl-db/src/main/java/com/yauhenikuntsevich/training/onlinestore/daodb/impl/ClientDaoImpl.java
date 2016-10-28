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

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.ClientMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

@Repository
public class ClientDaoImpl implements EntityDao<Client> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Client get(Long id) {
		return jdbcTemplate.queryForObject("select * from client where client_id = ?", new Object[] { id },
				new ClientMapper());
	}

	@Override
	public Long add(Client entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"client\" (first_name, last_name, age, blacklisted) VALUES (?, ?, ?, ?)",
						new String[] { "client_id" });
				ps.setString(1, entity.getFirstName());
				ps.setString(2, entity.getLastName());
				ps.setInt(3, entity.getAge());
				ps.setBoolean(4, entity.getBlacklisted());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void update(Client entity) {
		jdbcTemplate.update(
				"update client set first_name = ?, last_name = ? , age = ?, blacklisted = ? where client_id = ?",
				entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getBlacklisted(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from client where client_id = ?", id);
	}

	@Override
	public List<Client> getAll() {
		return jdbcTemplate.query("select * from client", new ClientMapper());
	}
}
