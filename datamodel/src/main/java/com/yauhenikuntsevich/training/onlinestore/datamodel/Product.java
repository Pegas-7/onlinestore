package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class Product extends AbstractModel implements Externalizable {
	private Category category;
	private String name;
	private Double price;
	private Integer quantityStore;

	public Product() {
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantityStore() {
		return quantityStore;
	}

	public void setQuantityStore(Integer quantityStore) {
		this.quantityStore = quantityStore;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		category = (Category) in.readObject();
		name = (String) in.readObject();
		price = (Double) in.readObject();
		quantityStore = (Integer) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(category);
		out.writeObject(name);
		out.writeDouble(price);
		out.writeInt(quantityStore);
	}
}
