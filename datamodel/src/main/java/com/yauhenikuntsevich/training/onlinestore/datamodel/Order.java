package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.time.LocalDateTime;

public class Order extends AbstractModel {
	private LocalDateTime dateOrder;
	private Integer productId;
	private Integer clientId;
	private Integer administratorId;
	
	public Order(LocalDateTime dateOrder, Integer productId, Integer clientId, Integer administratorId) {
		this.dateOrder = dateOrder;
		this.productId = productId;
		this.clientId = clientId;
		this.administratorId = administratorId;
	}

	public Order() {
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDateTime dateOrder) {
		this.dateOrder = dateOrder;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getAdministratorId() {
		return administratorId;
	}

	public void setAdministratorId(Integer administratorId) {
		this.administratorId = administratorId;
	}
}
