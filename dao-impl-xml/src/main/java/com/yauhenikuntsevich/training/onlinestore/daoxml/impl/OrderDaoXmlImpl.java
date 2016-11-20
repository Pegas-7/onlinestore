package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.daoxml.exception.XmlFileNotFoundedException;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

@Repository
public class OrderDaoXmlImpl extends AbstractEntityDaoXml implements EntityDao<Order> {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(Order.class.getSimpleName(), Order.class);
		file = new File(basePath + "/" + Order.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	@Override
	public Order get(Long id) {
		List<Order> allOrders = readCollection();

		for (Order order : allOrders) {
			if (order.getId().equals(id)) {
				return order;
			}
		}
		return null;
	}

	@Override
	public Long add(Order order) {
		List<Order> allOrders = readCollection();
		Long id = getNextId(allOrders);
		order.setId(id);
		allOrders.add(order);
		writeCollection(allOrders);
		return id;
	}

	@Override
	public void update(Order order) {
		List<Order> allOrders = readCollection();

		for (Order order2 : allOrders) {
			if (order2.getId().equals(order.getId())) {
				order2.setDateOrder(order.getDateOrder());
				order2.setClient(order.getClient());
				order2.setAdministrator(order.getAdministrator());
				order2.setPriceAllPurchases(order.getPriceAllPurchases());
				break;
			}
		}

		writeCollection(allOrders);
	}

	@Override
	public void delete(Long id) {
		List<Order> allOrders = readCollection();

		for (int i = 0; i < allOrders.size(); i++) {
			if (allOrders.get(i).getId().equals(id)) {
				allOrders.remove(i);
				break;
			}
		}
		writeCollection(allOrders);
	}

	@Override
	public List<Order> getAll() {
		return (List<Order>) readCollection();
	}

	private List<Order> readCollection() {
		return (List<Order>) xstream.fromXML(file);
	}

	private void writeCollection(List<Order> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<Order> allOrders) {
		return allOrders.size() == 0 ? 1l : allOrders.get(allOrders.size() - 1).getId() + 1;
	}
}
