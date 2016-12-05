package com.yauhenikuntsevich.training.onlinestore.web.controller.clientright;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
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
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderItemModel;

@RestController
@RequestMapping("/client/order_items")

public class OrderItemControllerClientRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private OrderItemService orderItemService;

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
	public ResponseEntity<Void> createNewOrderItem(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody OrderItemModel orderItemModel) {
		Order order = orderItemModel.getOrder();
		Long idClientFromOrderItemModel = clientService.get(order.getId()).getId();
		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		if (idClientFromOrderItemModel == idClientFromDb) {
			orderItemService.save(conversionService.convert(orderItemModel, OrderItem.class));
		} else
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateOwnOrderItem(@RequestHeader(value = "Authorization") String authorization,
			@RequestBody OrderItemModel orderItemModel, @PathVariable Long id) {
		Order order = orderItemModel.getOrder();
		Long idClientFromOrderItemModel = clientService.get(order.getId()).getId();
		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		if (idClientFromOrderItemModel == idClientFromDb) {
			OrderItem orderItem = conversionService.convert(orderItemModel, OrderItem.class);
			orderItem.setId(id);
			orderItemService.save(orderItem);
		} else
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOwnOrderItem(@RequestHeader(value = "Authorization") String authorization, @PathVariable Long id) {
		Long idClientFromOrderItemModel = orderItemService.get(id).getOrder().getClient().getId();
		Long idClientFromDb = clientService.getIdByFirstName(getFirstNameFromHeader(authorization));

		if (idClientFromOrderItemModel == idClientFromDb) {
			orderItemService.delete(id);
		} else
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Void>(HttpStatus.OK);
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
