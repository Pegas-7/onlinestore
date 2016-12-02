package com.yauhenikuntsevich.training.onlinestore.web.controller.clientright;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;
import com.yauhenikuntsevich.training.onlinestore.services.OrderService;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderModel;

@RestController
@RequestMapping("/client/orders")

public class OrderControllerClientRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private OrderService orderService;
	
	@Inject
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderModel>> getOwnOrders(@RequestHeader(value = "Authorization") String authorization) {
		String firstName = getFirstNameFromHeader(authorization);

		List<OrderModel> converted = new ArrayList<>();
		List<Order> allOrders = orderService.getOwnOrders(firstName);

		for (Order order : allOrders) {
			converted.add(conversionService.convert(order, OrderModel.class));
		}

		return new ResponseEntity<List<OrderModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewOrder(@RequestHeader(value = "Authorization") String authorization, @RequestBody OrderModel orderModel) {
		Client client = new Client();
		client.setId(clientService.getIdByFirstName(getFirstNameFromHeader(authorization)));
		orderModel.setClient(client);
		orderService.save(conversionService.convert(orderModel, Order.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
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
