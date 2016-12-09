package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

public interface OrderItemService {

	@Transactional
	List<OrderItem> saveAll(List<OrderItem> orderItems);

	@Transactional
	Long save(OrderItem orderItem);

	OrderItem get(Long id);

	List<OrderItem> getAll();

	boolean delete(Long id);

	List<OrderItem> getOwnOrderItems(String firstName);
}
