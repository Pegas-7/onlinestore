package com.yauhenikuntsevich.training.onlinestore.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModel;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelEn;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelRu;

@RestController
@RequestMapping("/admin/categories")

public class CategoryController {
	@Inject
	ConversionService conversionService;

	@Inject
	private CategoryService сategoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryModel>> getAll(@RequestParam(value = "lang", defaultValue = "en") String lang) {
		List<Category> allCategories = сategoryService.getAll();
		List<CategoryModel> converted = new ArrayList<>();
		if (lang.equals("ru")) {
			for (Category category : allCategories) {
				converted.add(conversionService.convert(category, CategoryModelRu.class));
			}
		} else {
			for (Category category : allCategories) {
				converted.add(conversionService.convert(category, CategoryModelEn.class));
			}
		}
		return new ResponseEntity<List<CategoryModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CategoryModelEn> getById(@PathVariable Long id) {
		Category category = null;
		try {
			category = сategoryService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<CategoryModelEn>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<CategoryModelEn>(conversionService.convert(category, CategoryModelEn.class),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewCategory(@RequestBody CategoryModelEn categoryModel) {
		сategoryService.save(conversionService.convert(categoryModel, Category.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateCategory(@RequestBody CategoryModelEn categoryModel, @PathVariable Long id) {
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
