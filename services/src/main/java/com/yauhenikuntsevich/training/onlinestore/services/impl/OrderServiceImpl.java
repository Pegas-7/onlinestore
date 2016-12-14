package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.OrderDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.OrderCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheOrder;

@Service
public class OrderServiceImpl implements OrderService {

	@Inject
	private OrderDao orderDao;

	public static OrderCaching orderCaching = ExternalizableCacheOrder.createInstanceOrderCaching();

	@Override
	public List<Order> saveAll(List<Order> orders) {
		List<Order> orders1 = new LinkedList<>();

		for (Order order2 : orders) {
			Long id = save(order2);
			order2.setId(id);
			orders1.add(order2);
		}

		return orders1;
	}

	@Override
	public Long save(Order order) {
		if (order.getId() == null) {
			return orderDao.add(order);
		} else {
			Integer rows = orderDao.update(order);
			if (rows > 0) {
				orderCaching.putInCache(order.getId(), order);
				return order.getId();
			}
			return -1L;
		}
	}

	@Override
	public Order get(Long id) {
		Order order = null;

		if (orderCaching.getCache().get(id) != null) {
			order = orderCaching.getCache().get(id);
		} else {
			order = orderDao.get(id);
			orderCaching.putInCache(id, order);
		}

		return order;
	}

	@Override
	public List<Order> getAll() {
		return orderDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		Integer rows = orderDao.delete(id);
		if (rows > 0) {
			orderCaching.deleteFromCache(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Order> getAllOrdersOneAdministrator(Long administratorId) {
		return orderDao.getAllOrdersOneAdministrator(administratorId);
	}

	@Override
	public List<Order> getAllOrdersOneClient(Long clientId) {
		return orderDao.getAllOrdersOneClient(clientId);
	}

	@Override
	public List<Order> getAllOrdersIntervalDate(Date after, Date before) {
		return orderDao.getAllOrdersIntervalDate(after, before);
	}

	@Override
	public List<Order> getOwnOrders(String firstName) {
		return orderDao.getOwnOrders(firstName);
	}

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheOrder.writeCacheInFile(orderCaching);
	}
}
