package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoXmlImpl implements EntityDao<Order> {

	@Override
	public Order get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long add(Order entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Order entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Order> getAll() {
		throw new UnsupportedOperationException();
	}
}
