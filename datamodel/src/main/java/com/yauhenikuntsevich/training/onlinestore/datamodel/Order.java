package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.sql.Date;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class Order extends AbstractModel {
	private Date dateOrder;
	private Client client;
	private Administrator administrator;
	private Double priceAllPurchases;

	public Order() {
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
