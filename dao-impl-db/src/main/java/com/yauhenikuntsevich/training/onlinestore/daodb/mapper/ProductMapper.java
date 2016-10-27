package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public final class ProductMapper implements RowMapper<Product> {
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long product_id = rs.getLong("product_id");
		Long categoryId = rs.getLong("category_id");
		String name = rs.getString("name");
		Integer price = rs.getInt("price");
		Integer quantity = rs.getInt("quantity");
		String item = rs.getString("item");

		Product entity = new Product();
		Category category = new Category();
		category.setId(categoryId);
		category.setItem(item);

		entity.setId(product_id);
		entity.setCategory(category);
		entity.setName(name);
		entity.setPrice(price);
		entity.setQuantity(quantity);

		return entity;
	}
}