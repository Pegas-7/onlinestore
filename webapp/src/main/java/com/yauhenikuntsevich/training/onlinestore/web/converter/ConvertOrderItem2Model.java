package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderItemModel;

public class ConvertOrderItem2Model implements Converter<OrderItem, OrderItemModel> {

	@Override
	public OrderItemModel convert(OrderItem orderItem) {
		OrderItemModel e = new OrderItemModel();
		e.setId(orderItem.getId());
		e.setOrder(orderItem.getOrder());
		e.setProduct(orderItem.getProduct());
		e.setQuantity(orderItem.getQuantity());
		return e;
	}
}
