package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.web.model.ProductModel;

public class ConvertModel2Product implements Converter<ProductModel, Product> {

	@Override
	public Product convert(ProductModel productModel) {
		Product e = new Product();
		e.setCategory(productModel.getCategory());
		e.setName(productModel.getName());
		e.setPrice(productModel.getPrice());
		e.setQuantity(productModel.getQuantity());
		return e;
	}
}
