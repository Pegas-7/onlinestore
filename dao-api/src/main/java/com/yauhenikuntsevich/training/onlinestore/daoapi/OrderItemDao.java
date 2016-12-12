package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

public interface OrderItemDao {
	OrderItem get(Long id);

	Long add(OrderItem entity);

	Integer update(OrderItem entity);

	Integer delete(Long id);

	List<OrderItem> getAll();
}
