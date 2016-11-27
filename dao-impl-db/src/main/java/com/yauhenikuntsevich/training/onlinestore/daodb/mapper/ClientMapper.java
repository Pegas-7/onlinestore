package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public final class ClientMapper implements RowMapper<Client> {
	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long client_id = rs.getLong("client_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		Integer age = rs.getInt("age");
		Boolean blacklisted = rs.getBoolean("blacklisted");
		String password = rs.getString("password");
		String role = rs.getString("role");
		
		Client entity = new Client();
		
		entity.setId(client_id);
		entity.setFirstName(firstName);
		entity.setLastName(lastName);
		entity.setAge(age);
		entity.setBlacklisted(blacklisted);
		entity.setPassword(password);
		entity.setRole(role);
		
		return entity;
	}
}