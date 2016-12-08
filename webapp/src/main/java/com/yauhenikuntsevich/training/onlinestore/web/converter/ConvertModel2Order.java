package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderModel;

public class ConvertModel2Order implements Converter<OrderModel, Order> {

	@Override
	public Order convert(OrderModel orderModel) {
		Order e = new Order();
		e.setId(orderModel.getId());
		e.setDateOrder(orderModel.getDateOrder());
		e.setClient(orderModel.getClient());
		e.setAdministrator(orderModel.getAdministrator());
		e.setPriceAllPurchases(orderModel.getPriceAllPurchases());
		return e;
	}
}
