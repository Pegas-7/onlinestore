package com.yauhenikuntsevich.training.onlinestore.datamodel;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class OrderItem extends AbstractModel {
	private Order order;
	private Product product;
	private Integer quantity;

	public OrderItem() {
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
