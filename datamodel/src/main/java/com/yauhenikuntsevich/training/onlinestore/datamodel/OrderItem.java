package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class OrderItem extends AbstractModel implements Externalizable {
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

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		order = (Order) in.readObject();
		product = (Product) in.readObject();
		quantity = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(order);
		out.writeObject(product);
		out.writeInt(quantity);
	}
}
