package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Inject
	private EntityDao<OrderItem> orderItemDao;

	@Override
	public List<OrderItem> saveAll(List<OrderItem> orderItems) {
		List<OrderItem> orderItems1 = new LinkedList<>();

		for (OrderItem orderItem2 : orderItems) {
			Long id = save(orderItem2);
			orderItem2.setId(id);
			orderItems1.add(orderItem2);
		}

		return orderItems1;
	}

	@Override
	public Long save(OrderItem orderItem) {
		if (orderItem.getId() == null) {
			return orderItemDao.add(orderItem);
		} else {
			orderItemDao.update(orderItem);
			return orderItem.getId();
		}
	}

	@Override
	public OrderItem get(Long id) {
		return orderItemDao.get(id);
	}

	@Override
	public List<OrderItem> getAll() {
		return orderItemDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		orderItemDao.delete(id);
		return true;
	}

	@Override
	public List<Product> getProductsOneOrder(Long orderId) {
		List<OrderItem> orderItems = orderItemDao.getAll();
		List<Product> productsForReturn = new LinkedList<>();

		for (OrderItem orderItem : orderItems) {
			if (orderItem.getOrder().getId() == orderId) {
				productsForReturn.add(orderItem.getProduct());
			}
		}

		return productsForReturn;
	}
}
