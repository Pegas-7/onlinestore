package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
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
	public ResponseEntity<String> createNewProduct(@RequestBody ProductModel productModel) {
		Long id;
		try {
			if (productModel.getQuantityStore() == null) {
				productModel.setQuantityStore(0);
			}
			id = productService.save(conversionService.convert(productModel, Product.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Product was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateProduct(@RequestBody ProductModel productModel, @PathVariable Long id) {
		if (productModel.getQuantityStore() == null) {
			productModel.setQuantityStore(0);
		}

		Product product = conversionService.convert(productModel, Product.class);
		product.setId(id);

		try {
			Long id1 = productService.save(product);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Product was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Product with id = " + id + " not found in database",
						HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Boolean deleted = productService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("Product with id = " + id + " was deleted from database", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Product with id = " + id + " not found in database", HttpStatus.NOT_FOUND);
	}
}
