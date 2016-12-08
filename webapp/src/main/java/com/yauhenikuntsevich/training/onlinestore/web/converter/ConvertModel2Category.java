package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.web.model.CategoryModel;

public class ConvertModel2Category implements Converter<CategoryModel, Category> {

	@Override
	public Category convert(CategoryModel categoryModel) {
		Category e = new Category();
		e.setId(categoryModel.getId());
		e.setItemEn(categoryModel.getItemEn());
		e.setItemRu(categoryModel.getItemRu());
		return e;
	}
}
