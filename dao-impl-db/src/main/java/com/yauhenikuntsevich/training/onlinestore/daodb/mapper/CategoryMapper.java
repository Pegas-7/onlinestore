package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public final class CategoryMapper implements RowMapper<Category> {
	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long category_id = rs.getLong("category_id");
		String itemEn = rs.getString("item_en");
		String itemRu = rs.getString("item_ru");
		Category entity = new Category();
		entity.setId(category_id);
		entity.setItemEn(itemEn);
		entity.setItemRu(itemRu);
		return entity;
	}
}