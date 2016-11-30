package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.web.model.ProductModel;

public class ConvertProduct2Model implements Converter<Product, ProductModel> {

	@Override
	public ProductModel convert(Product product) {
		ProductModel e = new ProductModel();
		e.setId(product.getId());
		e.setCategory(product.getCategory());
		e.setName(product.getName());
		e.setPrice(product.getPrice());
		e.setQuantity(product.getQuantity());
		return e;
	}
}
