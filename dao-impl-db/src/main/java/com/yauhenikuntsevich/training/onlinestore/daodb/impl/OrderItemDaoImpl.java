package com.yauhenikuntsevich.training.onlinestore.daodb.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daodb.OrderItemDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
	@Override
	public Map<Order, Map<Product, Integer>> getAllOrdersWithProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProductInOrder(Long order_id, Product product, Integer quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProductInOrder(Long order_id, Product product, Integer quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProductFromOrder(Long order_id, Product product) {
		// TODO Auto-generated method stub

	}
}
