package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.AdministratorMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@Repository
public class AdministratorDaoImpl implements EntityDao<Administrator> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Administrator get(Long id) {
		return jdbcTemplate.queryForObject("select * from administrator where administrator_id = ?",
				new Object[] { id }, new AdministratorMapper());
	}

	@Override
	public void add(Administrator entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Administrator entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Administrator> getAll() {
		return jdbcTemplate.query("select * from administrator", new AdministratorMapper());
	}
}
