package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public final class AdministratorMapper implements RowMapper<Administrator> {
	@Override
	public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long administrator_id = rs.getLong("administrator_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String password = rs.getString("password");
		String role = rs.getString("role");
		Administrator entity = new Administrator();
		entity.setId(administrator_id);
		entity.setFirstName(firstName);
		entity.setLastName(lastName);
		entity.setPassword(password);
		entity.setRole(role);
		return entity;
	}
}