package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Override
	public void saveAll(List<OrderItem> orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long save(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderItem get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderItem> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getProductsOneOrder(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
