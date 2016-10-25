package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.AdministratorDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@Repository
public class AdministratorDaoImplTest implements AdministratorDao {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public Administrator get(Long id) {
		return jdbcTemplate.queryForObject("select * from administrator where id = ?", new Object[] { id },
				new BeanPropertyRowMapper<Administrator>(Administrator.class));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer add(Administrator administrator) {
		// TODO Auto-generated method stub
		return null;
	}
}
