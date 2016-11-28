package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.OrderCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheOrder;

@Service
public class OrderServiceImpl implements OrderService {

	@Inject
	private EntityDao<Order> orderDao;

	public OrderCaching orderCaching = ExternalizableCacheOrder.createInstanceOrderCaching();

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
			orderDao.update(order);
			orderCaching.putInCache(order.getId(), order);
			return order.getId();
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
		orderDao.delete(id);
		orderCaching.deleteFromCache(id);
		return true;
	}

	@Override
	public List<Order> getAllOrdersOneAdministrator(Long administratorId) {
		List<Order> orders = orderDao.getAll();
		List<Order> ordersForReturn = new LinkedList<>();

		for (Order order : orders) {
			if (order.getAdministrator().getId() == administratorId) {
				ordersForReturn.add(order);
			}
		}

		return ordersForReturn;
	}

	@Override
	public List<Order> getAllOrdersOneClient(Long clientId) {
		List<Order> orders = orderDao.getAll();
		List<Order> ordersForReturn = new LinkedList<>();

		for (Order order : orders) {
			if (order.getClient().getId() == clientId) {
				ordersForReturn.add(order);
			}
		}

		return ordersForReturn;
	}

	@Override
	public List<Order> getAllOrdersIntervalDate(Date after, Date before) {
		List<Order> orders = orderDao.getAll();
		List<Order> ordersForReturn = new LinkedList<>();

		for (Order order : orders) {
			if (order.getDateOrder().after(after) && order.getDateOrder().before(before)) {
				ordersForReturn.add(order);
			}
		}

		return ordersForReturn;
	}

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheOrder.writeCacheInFile(orderCaching);
	}
}
