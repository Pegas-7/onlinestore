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
		Double price = rs.getDouble("price");
		Integer quantityStore = rs.getInt("quantity_store");
		String itemEn = rs.getString("item_en");
		String itemRu = rs.getString("item_ru");

		Product entity = new Product();

		Category category = new Category();
		category.setId(categoryId);
		category.setItemEn(itemEn);
		category.setItemRu(itemRu);

		entity.setId(product_id);
		entity.setCategory(category);
		entity.setName(name);
		entity.setPrice(price);
		entity.setQuantityStore(quantityStore);

		return entity;
	}
}