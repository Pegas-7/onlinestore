package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

public interface OrderDao {
	Order get(Long id);

	Integer create(Order order);

	void update(Order order);

	void delete(Long id);

	List<Order> getAll();
}
