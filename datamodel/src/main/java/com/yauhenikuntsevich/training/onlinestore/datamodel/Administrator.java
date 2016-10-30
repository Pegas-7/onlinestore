package com.yauhenikuntsevich.training.onlinestore.datamodel;

import com.yauhenikuntsevich.training.onlinestore.services.impl.AdministratorServiceImpl;

public class Administrator extends AbstractModel {
	private String firstName;
	private String lastName;

	public Administrator() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
