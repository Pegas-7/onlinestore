package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public final class OrderItemMapper implements RowMapper<OrderItem> {
	@Override
	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long orderId = rs.getLong("order_id");
		Date dateOrder = rs.getDate("date_order");
		Long clientId = rs.getLong("client_id");
		Long administratorId = rs.getLong("administrator_id");
		Double priceAllPurchases = rs.getDouble("price_all_purchases");
		String firstNameClient = rs.getString("first_name_client");
		String lastNameClient = rs.getString("last_name_client");
		Integer age = rs.getInt("age");
		Boolean blacklisted = rs.getBoolean("blacklisted");
		String passwordClient = rs.getString("password_client");
		String roleClient = rs.getString("role_client");
		String firstNameAdministrator = rs.getString("first_name");
		String lastNameAdministrator = rs.getString("last_name");
		String passwordAdministrator = rs.getString("password");
		String roleAdministrator = rs.getString("role");

		Client client = new Client();
		client.setId(clientId);
		client.setFirstName(firstNameClient);
		client.setLastName(lastNameClient);
		client.setAge(age);
		client.setBlacklisted(blacklisted);
		client.setPassword(passwordClient);
		client.setRole(roleClient);

		Administrator administrator = new Administrator();
		administrator.setId(administratorId);
		administrator.setFirstName(firstNameAdministrator);
		administrator.setLastName(lastNameAdministrator);
		administrator.setPassword(passwordAdministrator);
		administrator.setRole(roleAdministrator);

		Order order = new Order();
		order.setId(orderId);
		order.setDateOrder(dateOrder);
		order.setClient(client);
		order.setAdministrator(administrator);
		order.setPriceAllPurchases(priceAllPurchases);

		Long product_id = rs.getLong("product_id");
		Long categoryId = rs.getLong("category_id");
		String name = rs.getString("name");
		Double price = rs.getDouble("price");
		Integer quantity = rs.getInt("quantity");
		String itemEn = rs.getString("item_en");
		String itemRu = rs.getString("item_ru");

		Category category = new Category();
		category.setId(categoryId);
		category.setItemEn(itemEn);
		category.setItemRu(itemRu);

		Product product = new Product();
		product.setId(product_id);
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setQuantity(quantity);

		Integer quantity1 = rs.getInt("quantity");
		Long orderItemId = rs.getLong("order_item_id");

		OrderItem orderItem = new OrderItem();
		orderItem.setId(orderItemId);
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity1);

		return orderItem;
	}
}