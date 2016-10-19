package com.yauhenikuntsevich.training.onlinestore.datamodel;

public class Product extends AbstractModel {
	private String item;
	private Integer price;

	public Product(String item, Integer price) {
		this.item = item;
		this.price = price;
	}

	public Product() {
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
