package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public void saveAll(List<Order> order) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long save(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Order> getAllOrdersOneAdministrator(Long administratorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrdersOneClient(Long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrdersIntervalDate(Date before, Date after) {
		// TODO Auto-generated method stub
		return null;
	}

}
