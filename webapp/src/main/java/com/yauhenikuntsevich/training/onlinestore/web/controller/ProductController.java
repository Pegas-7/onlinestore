package com.yauhenikuntsevich.training.onlinestore.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.ProductService;
import com.yauhenikuntsevich.training.onlinestore.web.model.ProductModel;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Inject
	ConversionService conversionService;

	@Inject
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProductModel>> getAll(
			@RequestParam(value = "categoryId", defaultValue = "-1") String categoryId) {
		Long id = Long.valueOf(categoryId);

		List<ProductModel> converted = new ArrayList<>();

		if (id != -1L) {
			List<Product> allProductsWithOneCategory = productService.getAllProductsWithOneCategory(id);

			for (Product product : allProductsWithOneCategory) {
				converted.add(conversionService.convert(product, ProductModel.class));
			}
		} else {
			List<Product> allProducts = productService.getAll();

			for (Product product : allProducts) {
				converted.add(conversionService.convert(product, ProductModel.class));
			}
		}

		return new ResponseEntity<List<ProductModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductModel> getById(@PathVariable Long id) {
		Product product = null;

		try {
			product = productService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<ProductModel>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<ProductModel>(conversionService.convert(product, ProductModel.class), HttpStatus.OK);
	}
}
