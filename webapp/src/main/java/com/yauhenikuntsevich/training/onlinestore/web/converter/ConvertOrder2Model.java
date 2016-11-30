package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderModel;

public class ConvertOrder2Model implements Converter<Order, OrderModel> {

	@Override
	public OrderModel convert(Order order) {
		OrderModel e = new OrderModel();
		e.setId(order.getId());
		e.setDateOrder(order.getDateOrder());
		e.setClient(order.getClient());
		e.setAdministrator(order.getAdministrator());
		e.setPriceAllPurchases(order.getPriceAllPurchases());
		return e;
	}
}
