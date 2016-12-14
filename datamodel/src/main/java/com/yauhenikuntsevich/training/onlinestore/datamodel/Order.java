package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Date;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class Order extends AbstractModel implements Externalizable {
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

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		dateOrder = (Date) in.readObject();
		client = (Client) in.readObject();
		administrator = (Administrator) in.readObject();
		priceAllPurchases = in.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(dateOrder);
		out.writeObject(client);
		out.writeObject(administrator);
		out.writeDouble(priceAllPurchases);
	}
}
