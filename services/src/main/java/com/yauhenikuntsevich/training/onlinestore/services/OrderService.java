package com.yauhenikuntsevich.training.onlinestore.services;

import java.sql.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;

public interface OrderService {

	@Transactional
	List<Order> saveAll(List<Order> order);

	Long save(Order order);

	Order get(Long id);

	List<Order> getAll();

	boolean delete(Long id);

	List<Order> getAllOrdersOneAdministrator(Long administratorId);

	List<Order> getAllOrdersOneClient(Long clientId);

	List<Order> getAllOrdersIntervalDate(Date before, Date after);
}
