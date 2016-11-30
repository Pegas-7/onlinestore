package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModelRu;

public class ConvertCategoryRu2Model implements Converter<Category, CategoryModelRu> {

	@Override
	public CategoryModelRu convert(Category category) {
		CategoryModelRu e = new CategoryModelRu();
		e.setId(category.getId());
		e.setItemRu(category.getItemRu());
		return e;
	}
}