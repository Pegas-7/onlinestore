package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public class PersonAbstractModelMapper implements RowMapper<PersonAbstractModel> {
	@Override
	public PersonAbstractModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		String firstName = rs.getString("first_name");
		String password = rs.getString("password");
		String role = rs.getString("role");

		PersonAbstractModel entity = new PersonAbstractModel();

		entity.setFirstName(firstName);
		entity.setPassword(password);
		entity.setRole(role);
		return entity;
	}
}
