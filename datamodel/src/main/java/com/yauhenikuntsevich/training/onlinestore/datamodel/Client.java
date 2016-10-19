package com.yauhenikuntsevich.training.onlinestore.datamodel;

public class Client extends AbstractModel {
	private String firstName;
	private String lastName;
	private Integer age;
	private Integer amountOrders;
	private Boolean isInBlackList;

	public Client(String firstName, String lastName, Integer age, Integer amountOrders, Boolean isInBlackList) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.amountOrders = amountOrders;
		this.isInBlackList = isInBlackList;
	}

	public Client() {
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAmountOrders() {
		return amountOrders;
	}

	public void setAmountOrders(Integer amountOrders) {
		this.amountOrders = amountOrders;
	}

	public Boolean getIsInBlackList() {
		return isInBlackList;
	}

	public void setIsInBlackList(Boolean isInBlackList) {
		this.isInBlackList = isInBlackList;
	}
}
