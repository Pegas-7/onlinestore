package com.yauhenikuntsevich.training.onlinestore.daoxml.listholders;

import java.util.ArrayList;
import java.util.List;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

public class AdministratorListHolder {
	private List<Administrator> list;

	public AdministratorListHolder() {
		list = new ArrayList<Administrator>();
	}

	public void add(Administrator administrator) {
		list.add(administrator);
	}
}
