package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class ClientIdMapper implements RowMapper<Long> {
	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getLong("client_id");
	}
}