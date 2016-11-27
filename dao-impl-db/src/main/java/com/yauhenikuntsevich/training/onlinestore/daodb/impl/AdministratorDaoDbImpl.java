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
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.AdministratorMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@Repository
public class AdministratorDaoDbImpl implements EntityDao<Administrator> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Administrator get(Long id) {
		return jdbcTemplate.queryForObject("select * from administrator where administrator_id = ?",
				new Object[] { id }, new AdministratorMapper());
	}

	@Override
	public Long add(Administrator entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO \"administrator\" (first_name, last_name, password, role) VALUES (?, ?, ?, ?)",
						new String[] { "administrator_id" });
				ps.setString(1, entity.getFirstName());
				ps.setString(2, entity.getLastName());
				ps.setString(3, entity.getPassword());
				ps.setString(4, entity.getRole());
				return ps;
			}

		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void update(Administrator entity) {
		jdbcTemplate.update(
				"update administrator set first_name = ?, last_name = ?, password = ?, role = ? where administrator_id = ?",
				entity.getFirstName(), entity.getLastName(), entity.getPassword(), entity.getRole(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from administrator where administrator_id = ?", id);
	}

	@Override
	public List<Administrator> getAll() {
		return jdbcTemplate.query("select * from administrator", new AdministratorMapper());
	}
}
