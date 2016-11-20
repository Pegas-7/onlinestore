package com.yauhenikuntsevich.training.onlinestore.daodb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

public final class OrderMapper implements RowMapper<Order> {
	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long orderId = rs.getLong("order_id");
		Date dateOrder = rs.getDate("date_order");
		Long clientId = rs.getLong("client_id");
		Long administratorId = rs.getLong("administrator_id");
		Double priceAllPurchases = rs.getDouble("price_all_purchases");
		String firstNameClient = rs.getString("first_name_client");
		String lastNameClient = rs.getString("last_name_client");
		Integer age = rs.getInt("age");
		Boolean blacklisted = rs.getBoolean("blacklisted");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");

		Client client = new Client();
		client.setId(clientId);
		client.setFirstName(firstNameClient);
		client.setLastName(lastNameClient);
		client.setAge(age);
		client.setBlacklisted(blacklisted);

		Administrator administrator = new Administrator();
		administrator.setId(administratorId);
		administrator.setFirstName(firstName);
		administrator.setLastName(lastName);

		Order entity = new Order();
		entity.setId(orderId);
		entity.setDateOrder(dateOrder);
		entity.setClient(client);
		entity.setAdministrator(administrator);
		entity.setPriceAllPurchases(priceAllPurchases);

		return entity;
	}
}