package com.yauhenikuntsevich.training.onlinestore.web.model;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public class OrderItemModel {
	private String id;
	private Order order;
	private Product product;
	private Integer quantity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
