package com.yauhenikuntsevich.training.onlinestore.daodb.util;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AuthenticationDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.mapper.PersonAbstractModelMapper;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

@Repository
public class AuthenticationAdministratorDaoDbImpl implements AuthenticationDao<Administrator> {
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public PersonAbstractModel get(String username) {
		return jdbcTemplate.queryForObject("select first_name, password, role from administrator where first_name = ?",
				new Object[] { username }, new PersonAbstractModelMapper());
	}
}
