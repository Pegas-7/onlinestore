package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public final class AdministratorMapper implements RowMapper<Administrator> {
	@Override
	public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = rs.getLong("id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		Administrator entity = new Administrator();
		entity.setId(id);
		entity.setFirstName(firstName);
		entity.setLastName(lastName);
		return entity;
	}
}