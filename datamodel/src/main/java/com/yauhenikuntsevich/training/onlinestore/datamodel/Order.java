package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.time.LocalDateTime;

public class Order extends AbstractModel {
	private LocalDateTime dateOrder;
	private Client client;
	private Administrator administrator;
	private Integer priceAllPurchases;

	public Order() {
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDateTime dateOrder) {
		this.dateOrder = dateOrder;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public Integer getPriceAllPurchases() {
		return priceAllPurchases;
	}

	public void setPriceAllPurchases(Integer priceAllPurchases) {
		this.priceAllPurchases = priceAllPurchases;
	}
}
