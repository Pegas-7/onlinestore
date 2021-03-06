package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderModel;

@RestController
@RequestMapping("/admin/orders")

public class OrderControllerAdminRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderModel>> getAll(
			@RequestParam(value = "administrator_id", defaultValue = "-1") String administratorId,
			@RequestParam(value = "client_id", defaultValue = "-1") String clientId,
			@RequestParam(value = "interval", defaultValue = "-1_-1") String interval) {

		Long administratorId1 = Long.valueOf(administratorId);
		Long clientId1 = Long.valueOf(clientId);
		String[] interval1 = interval.split("_");
		Long beforeDate1 = Long.valueOf(interval1[0]);
		Long afterDate1 = Long.valueOf(interval1[1]);

		return checkRequestParam(administratorId1, clientId1, beforeDate1, afterDate1);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderModel> getById(@PathVariable Long id) {
		Order order = null;
		try {
			order = orderService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<OrderModel>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OrderModel>(conversionService.convert(order, OrderModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewOrder(@RequestBody OrderModel orderModel) {
		Long id;
		try {
			id = orderService.save(conversionService.convert(orderModel, Order.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Order was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateOrder(@RequestBody OrderModel orderModel, @PathVariable Long id) {
		Order order = conversionService.convert(orderModel, Order.class);
		order.setId(id);

		try {
			Long id1 = orderService.save(order);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Order was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Order with id = " + id + " not found in database",
						HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Boolean deleted = orderService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("Order with id = " + id + " was deleted from database", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Order with id = " + id + " not found in database", HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<List<OrderModel>> checkRequestParam(Long administratorId1, Long clientId1, Long beforeDate1,
			Long afterDate1) {
		List<OrderModel> converted = new ArrayList<>();

		if (administratorId1 != -1L) {
			List<Order> allOrders = orderService.getAllOrdersOneAdministrator(administratorId1);

			for (Order order : allOrders) {
				converted.add(conversionService.convert(order, OrderModel.class));
			}

			return new ResponseEntity<List<OrderModel>>(converted, HttpStatus.OK);
		}
		if (clientId1 != -1L) {
			List<Order> allOrders = orderService.getAllOrdersOneClient(clientId1);

			for (Order order : allOrders) {
				converted.add(conversionService.convert(order, OrderModel.class));
			}

			return new ResponseEntity<List<OrderModel>>(converted, HttpStatus.OK);
		}
		if (beforeDate1 != -1L && afterDate1 != -1L) {
			List<Order> allOrders = orderService.getAllOrdersIntervalDate(new Date(afterDate1), new Date(beforeDate1));

			for (Order order : allOrders) {
				converted.add(conversionService.convert(order, OrderModel.class));
			}

			return new ResponseEntity<List<OrderModel>>(converted, HttpStatus.OK);
		} else {
			List<Order> allOrders = orderService.getAll();

			for (Order order : allOrders) {
				converted.add(conversionService.convert(order, OrderModel.class));
			}

			return new ResponseEntity<List<OrderModel>>(converted, HttpStatus.OK);
		}
	}
}
