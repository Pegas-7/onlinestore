package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public final class CategoryMapper implements RowMapper<Category> {
	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = rs.getLong("id");
		String item = rs.getString("item");
		Category entity = new Category();
		entity.setId(id);
		entity.setItem(item);
		return entity;
	}
}