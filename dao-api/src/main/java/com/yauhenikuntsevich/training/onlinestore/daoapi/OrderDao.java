package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.sql.Date;
import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

public interface OrderDao {
	Order get(Long id);

	Long add(Order entity);

	Integer update(Order entity);

	Integer delete(Long id);

	List<Order> getAll();

	List<Order> getOwnOrders(String firstName);

	List<Order> getAllOrdersOneAdministrator(Long administratorId);

	List<Order> getAllOrdersOneClient(Long clientId);

	List<Order> getAllOrdersIntervalDate(Date before, Date after);
}
