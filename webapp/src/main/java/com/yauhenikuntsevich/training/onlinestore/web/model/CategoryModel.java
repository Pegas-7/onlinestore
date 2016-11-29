package com.yauhenikuntsevich.training.onlinestore.web.model;

public class CategoryModel {
	private Long id;
	private String itemEn;
	private String itemRu;

	public String getItemEn() {
		return itemEn;
	}

	public void setItemEn(String itemEn) {
		this.itemEn = itemEn;
	}

	public String getItemRu() {
		return itemRu;
	}

	public void setItemRu(String itemRu) {
		this.itemRu = itemRu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
