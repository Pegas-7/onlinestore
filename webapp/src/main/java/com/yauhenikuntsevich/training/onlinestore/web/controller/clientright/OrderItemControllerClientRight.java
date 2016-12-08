package com.yauhenikuntsevich.training.onlinestore.web.controller.clientright;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;
import com.yauhenikuntsevich.training.onlinestore.services.OrderItemService;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;
import com.yauhenikuntsevich.training.onlinestore.services.exception.NotEnoughQuantityProductException;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderItemModel;

@RestController
@RequestMapping("/client/order_items")

public class OrderItemControllerClientRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private OrderItemService orderItemService;

	@Inject
	private OrderService orderService;

	@Inject
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemModel>> getOwnOrders(
			@RequestHeader(value = "Authorization") String authorization) {
		String firstName = getFirstNameFromHeader(authorization);

		List<OrderItemModel> converted = new ArrayList<>();
		List<OrderItem> allOrderItems = orderItemService.getOwnOrderItems(firstName);

		for (OrderItem orderItem : allOrderItems) {
			converted.add(conversionService.convert(orderItem, OrderItemModel.class));
		}

		return new ResponseEntity<List<OrderItemModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewOrderItem(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody OrderItemModel orderItemModel) {
		if (orderItemModel.getQuantity() == null) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Order order = orderItemModel.getOrder();
		Long idClientFromOrderItemModel = orderService.get(order.getId()).getClient().getId();
		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		Long id = 0L;
		if (idClientFromOrderItemModel == idClientFromDb) {
			try {
				id = orderItemService.save(conversionService.convert(orderItemModel, OrderItem.class));
			} catch (NotEnoughQuantityProductException e) {
				return new ResponseEntity<String>("Not enough quantity product in the stock", HttpStatus.OK);
			}

		} else
			return new ResponseEntity<String>("Attempt to create order item using no own order",
					HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<String>("OrderItem was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateOwnOrderItem(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody OrderItemModel orderItemModel, @PathVariable Long id) {
		if (orderItemModel.getQuantity() == null) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Order order = orderItemModel.getOrder();
		Long idClientFromOrderItemModel = orderService.get(order.getId()).getClient().getId();
		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		Long id1 = 0L;
		if (idClientFromOrderItemModel == idClientFromDb) {
			OrderItem orderItem = conversionService.convert(orderItemModel, OrderItem.class);
			orderItem.setId(id);

			try {
				id1 = orderItemService.save(orderItem);
				if (id1 != -1L) {
					return new ResponseEntity<String>("OrderItem was updated in database", HttpStatus.OK);
				}
			} catch (NotEnoughQuantityProductException e) {
				return new ResponseEntity<String>("Not enough quantity product in the stock", HttpStatus.OK);
			}
		} else
			return new ResponseEntity<String>("Attempt to update order item using no own order",
					HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOwnOrderItem(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable Long id) {

		Long idClientFromOrderItemModel = null;
		try {
			idClientFromOrderItemModel = orderItemService.get(id).getOrder().getClient().getId();
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<String>("OrderItem with id = " + id + " not found in database",
					HttpStatus.NOT_FOUND);
		}

		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		if (idClientFromOrderItemModel == idClientFromDb) {
			if (orderItemService.delete(id)) {
				return new ResponseEntity<String>("OrderItem with id = " + id + " was deleted from database",
						HttpStatus.OK);
			}
		} else
			return new ResponseEntity<String>("Attempt to delete no own order item", HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	private String getFirstNameFromHeader(String authorization) {
		try {
			String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			return credentials.split(":", 2)[0];
		} catch (Exception e) {
			return null;
		}
	}
}
