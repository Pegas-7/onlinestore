package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelEn;

public class ConvertCategoryEn2Model implements Converter<Category, CategoryModelEn> {

	@Override
	public CategoryModelEn convert(Category category) {
		CategoryModelEn e = new CategoryModelEn();
		e.setId(category.getId());
		e.setItem(category.getItemEn());
		return e;
	}
}
