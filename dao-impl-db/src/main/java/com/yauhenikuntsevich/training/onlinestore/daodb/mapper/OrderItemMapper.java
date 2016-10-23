package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public final class OrderItemMapper implements RowMapper<OrderItem> {
	@Override
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = rs.getLong("id");
		String item = rs.getString("item");
		OrderItem entity = new OrderItem();
		entity.setId(id);
		entity.setItem(item);
		return entity;
	}
}