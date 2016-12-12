package com.yauhenikuntsevich.training.onlinestore.datamodel;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public class Client extends PersonAbstractModel {
	private Integer age;
	private Boolean blacklisted;

	public Client() {
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(Boolean blacklisted) {
		this.blacklisted = blacklisted;
	}
}
