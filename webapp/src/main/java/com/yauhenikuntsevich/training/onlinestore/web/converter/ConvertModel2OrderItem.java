package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.web.model.OrderItemModel;

public class ConvertModel2OrderItem implements Converter<OrderItemModel, OrderItem> {

	@Override
	public OrderItem convert(OrderItemModel orderItemModel) {
		OrderItem e = new OrderItem();
		e.setOrder(orderItemModel.getOrder());
		e.setProduct(orderItemModel.getProduct());
		e.setQuantity(orderItemModel.getQuantity());
		return e;
	}
}
