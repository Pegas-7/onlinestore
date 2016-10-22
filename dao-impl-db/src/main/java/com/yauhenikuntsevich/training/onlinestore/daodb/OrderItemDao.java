package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.Map;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public interface OrderItemDao {
	void addProductInOrder(Long order_id, Product product, Integer quantity);

	void updateProductInOrder(Long order_id, Product product, Integer quantity);

	void deleteProductFromOrder(Long order_id, Product product);

	Map<Order, Map<Product, Integer>> getAllOrdersWithProducts();
}
