package com.yauhenikuntsevich.training.onlinestore.web.model;

public abstract class CategoryModelDiffLang {
	private Long id;
	private String item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
