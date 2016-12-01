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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.services.CategoryService;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelDiffLang;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelEn;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelRu;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Inject
	ConversionService conversionService;

	@Inject
	private CategoryService сategoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryModelDiffLang>> getAll(
			@RequestParam(value = "lang", defaultValue = "en") String lang) {
		List<Category> allCategories = сategoryService.getAll();
		List<CategoryModelDiffLang> converted = new ArrayList<>();
		if (lang.equals("ru")) {
			for (Category category : allCategories) {
				converted.add(conversionService.convert(category, CategoryModelRu.class));
			}
		} else {
			for (Category category : allCategories) {
				converted.add(conversionService.convert(category, CategoryModelEn.class));
			}
		}
		return new ResponseEntity<List<CategoryModelDiffLang>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CategoryModelDiffLang> getById(@RequestParam(value = "lang", defaultValue = "en") String lang,
			@PathVariable Long id) {
		Category category = null;
		try {
			category = сategoryService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<CategoryModelDiffLang>(HttpStatus.NO_CONTENT);
		}
		CategoryModelDiffLang categoryModelDiffLang = null;
		if (lang.equals("ru")) {
			categoryModelDiffLang = conversionService.convert(category, CategoryModelRu.class);
		} else {
			categoryModelDiffLang = (conversionService.convert(category, CategoryModelEn.class));

		}
		return new ResponseEntity<CategoryModelDiffLang>(categoryModelDiffLang, HttpStatus.OK);
	}
}
