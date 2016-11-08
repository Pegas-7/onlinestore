package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable{
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
