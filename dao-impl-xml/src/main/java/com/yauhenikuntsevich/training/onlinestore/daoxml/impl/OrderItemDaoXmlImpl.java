package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

@Repository
public class OrderItemDaoXmlImpl implements EntityDao<OrderItem> {

	@Override
	public OrderItem get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long add(OrderItem entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(OrderItem entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<OrderItem> getAll() {
		throw new UnsupportedOperationException();
	}
}
