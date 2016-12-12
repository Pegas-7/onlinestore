package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

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
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.services.OrderItemService;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderItemModel;

@RestController
@RequestMapping("/admin/order_items")

public class OrderItemControllerAdminRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private OrderItemService orderItemService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemModel>> getAll() {

		List<OrderItem> allOrderItems = orderItemService.getAll();
		List<OrderItemModel> converted = new ArrayList<>();
		for (OrderItem orderItem : allOrderItems) {
			converted.add(conversionService.convert(orderItem, OrderItemModel.class));
		}

		return new ResponseEntity<List<OrderItemModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderItemModel> getById(@PathVariable Long id) {
		OrderItem orderItem = null;
		try {
			orderItem = orderItemService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<OrderItemModel>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OrderItemModel>(conversionService.convert(orderItem, OrderItemModel.class),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewOrder(@RequestBody OrderItemModel orderItemModel) {
		Long id;
		try {
			if (orderItemModel.getQuantity() == null) {
				throw new DataIntegrityViolationException(null);
			}
			id = orderItemService.save(conversionService.convert(orderItemModel, OrderItem.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations relations table's fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		

		return new ResponseEntity<String>("OrderItem was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateOrder(@RequestBody OrderItemModel orderItemModel, @PathVariable Long id) {
		OrderItem orderItem = conversionService.convert(orderItemModel, OrderItem.class);
		orderItem.setId(id);

		try {
			Long id1 = orderItemService.save(orderItem);
			if (id1 != -1L) {
				return new ResponseEntity<String>("OrderItem was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("OrderItem with id = " + id + " not found in database",
						HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations relations table's fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Boolean deleted = orderItemService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("OrderItem with id = " + id + " was deleted from database",
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("OrderItem with id = " + id + " not found in database", HttpStatus.NOT_FOUND);
	}
}
