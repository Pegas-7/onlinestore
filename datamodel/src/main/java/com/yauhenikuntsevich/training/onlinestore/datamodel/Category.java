package com.yauhenikuntsevich.training.onlinestore.datamodel;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class Category extends AbstractModel {
	String item;

	public Category() {
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
