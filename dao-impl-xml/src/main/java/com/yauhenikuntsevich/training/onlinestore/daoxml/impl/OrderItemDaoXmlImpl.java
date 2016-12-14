package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.OrderItemDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.daoxml.exception.XmlFileNotFoundedException;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;

@Repository
public class OrderItemDaoXmlImpl extends AbstractEntityDaoXml implements OrderItemDao {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(OrderItem.class.getSimpleName(), OrderItem.class);
		file = new File(basePath + "/" + OrderItem.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	@Override
	public OrderItem get(Long id) {
		List<OrderItem> allOrderItems = readCollection();

		for (OrderItem orderItem : allOrderItems) {
			if (orderItem.getId().equals(id)) {
				return orderItem;
			}
		}
		return null;
	}

	@Override
	public Long add(OrderItem orderItem) {
		List<OrderItem> allOrderItems = readCollection();
		Long id = getNextId(allOrderItems);
		orderItem.setId(id);
		allOrderItems.add(orderItem);
		writeCollection(allOrderItems);
		return id;
	}

	@Override
	public Integer update(OrderItem orderItem) {
		List<OrderItem> allOrderItems = readCollection();

		for (OrderItem orderItem2 : allOrderItems) {
			if (orderItem2.getId().equals(orderItem.getId())) {
				orderItem2.setOrder(orderItem.getOrder());
				orderItem2.setProduct(orderItem.getProduct());
				orderItem2.setQuantity(orderItem.getQuantity());

				break;
			}
		}

		writeCollection(allOrderItems);
		return 1;
	}

	@Override
	public Integer delete(Long id) {
		List<OrderItem> allOrderItems = readCollection();

		for (int i = 0; i < allOrderItems.size(); i++) {
			if (allOrderItems.get(i).getId().equals(id)) {
				allOrderItems.remove(i);
				break;
			}
		}
		writeCollection(allOrderItems);
		return 1;
	}

	@Override
	public List<OrderItem> getAll() {
		return (List<OrderItem>) readCollection();
	}

	private List<OrderItem> readCollection() {
		return (List<OrderItem>) xstream.fromXML(file);
	}

	private void writeCollection(List<OrderItem> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<OrderItem> allOrderItems) {
		return allOrderItems.size() == 0 ? 1l : allOrderItems.get(allOrderItems.size() - 1).getId() + 1;
	}
}
