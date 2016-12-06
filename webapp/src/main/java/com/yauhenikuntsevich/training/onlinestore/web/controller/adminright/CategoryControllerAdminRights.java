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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModel;

@RestController
@RequestMapping("/admin/categories")
public class CategoryControllerAdminRights {
	@Inject
	ConversionService conversionService;

	@Inject
	private CategoryService сategoryService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewCategory(@RequestBody CategoryModel categoryModel) {
		Long id;
		try {
			id = сategoryService.save(conversionService.convert(categoryModel, Category.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Category was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateCategory(@RequestBody CategoryModel categoryModel, @PathVariable Long id) {
		Category category = conversionService.convert(categoryModel, Category.class);
		category.setId(id);

		try {
			Long id1 = сategoryService.save(category);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Category was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Category with id = " + id + " not found in database",
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
		Boolean deleted = сategoryService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("Category with id = " + id + " was deleted from database", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Category with id = " + id + " not found in database", HttpStatus.NOT_FOUND);
	}
}
