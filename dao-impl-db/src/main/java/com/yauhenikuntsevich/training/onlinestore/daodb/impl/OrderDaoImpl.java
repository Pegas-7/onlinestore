package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.OrderDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoImpl implements OrderDao{
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
