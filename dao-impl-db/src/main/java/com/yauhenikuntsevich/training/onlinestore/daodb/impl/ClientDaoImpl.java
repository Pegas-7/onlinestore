package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
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
	public void add(Client entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Client entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Client> getAll() {
		return jdbcTemplate.query("select * from client", new ClientMapper());
	}
}
