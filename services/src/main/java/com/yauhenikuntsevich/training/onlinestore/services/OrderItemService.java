package com.yauhenikuntsevich.training.onlinestore.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public interface OrderItemService {

	@Transactional
	List<OrderItem> saveAll(List<OrderItem> orderItems);

	Long save(OrderItem orderItem);

	OrderItem get(Long id);

	List<OrderItem> getAll();

	boolean delete(Long id);

	List<Product> getProductsOneOrder(Long orderId);
}
