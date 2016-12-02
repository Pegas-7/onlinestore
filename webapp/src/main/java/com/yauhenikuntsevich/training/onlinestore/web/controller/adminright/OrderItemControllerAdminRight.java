package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
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
			return new ResponseEntity<OrderItemModel>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<OrderItemModel>(conversionService.convert(orderItem, OrderItemModel.class),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewOrder(@RequestBody OrderItemModel orderItemModel) {
		orderItemService.save(conversionService.convert(orderItemModel, OrderItem.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateOrder(@RequestBody OrderItemModel orderItemModel, @PathVariable Long id) {
		OrderItem orderItem = conversionService.convert(orderItemModel, OrderItem.class);
		orderItem.setId(id);
		orderItemService.save(orderItem);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		orderItemService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
