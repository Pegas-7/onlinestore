package com.yauhenikuntsevich.training.onlinestore.web.model;

import java.sql.Date;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

public class OrderModel {
	private Long id;
	private Date dateOrder;
	private Client client;
	private Administrator administrator;
	private Double priceAllPurchases;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
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

	public Double getPriceAllPurchases() {
		return priceAllPurchases;
	}

	public void setPriceAllPurchases(Double priceAllPurchases) {
		this.priceAllPurchases = priceAllPurchases;
	}
}
