package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.ProductService;
import com.yauhenikuntsevich.training.onlinestore.web.model.ProductModel;

@RestController
@RequestMapping("/admin/products")
public class ProductControllerAdminRights {
	@Inject
	ConversionService conversionService;

	@Inject
	private ProductService productService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewProduct(@RequestBody ProductModel productModel) {
		productService.save(conversionService.convert(productModel, Product.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateProduct(@RequestBody ProductModel productModel, @PathVariable Long id) {
		Product product = conversionService.convert(productModel, Product.class);
		product.setId(id);
		productService.save(product);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
