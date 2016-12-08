package com.yauhenikuntsevich.training.onlinestore.web.model;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

public class ProductModel {
	private Long id;
	private Category category;
	private String name;
	private Double price;
	private Integer quantityStore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
