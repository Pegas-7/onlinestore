package com.yauhenikuntsevich.training.onlinestore.web.controller;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
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
	public ResponseEntity<Void> createNewCategory(@RequestBody CategoryModel categoryModel) {
		сategoryService.save(conversionService.convert(categoryModel, Category.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateCategory(@RequestBody CategoryModel categoryModel, @PathVariable Long id) {
		Category category = conversionService.convert(categoryModel, Category.class);
		category.setId(id);
		сategoryService.save(category);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		сategoryService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
