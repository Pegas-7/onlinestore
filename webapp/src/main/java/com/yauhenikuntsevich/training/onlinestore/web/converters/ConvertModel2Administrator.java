package com.yauhenikuntsevich.training.onlinestore.web.converters;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.web.model.AdministratorModel;

public class ConvertModel2Administrator implements Converter<AdministratorModel, Administrator> {

	@Override
	public Administrator convert(AdministratorModel administratorModel) {
		Administrator e = new Administrator();
		e.setFirstName(administratorModel.getFirstName());
		e.setLastName(administratorModel.getLastName());
		e.setPassword(administratorModel.getPassword());
		e.setRole(administratorModel.getRole());
		return e;
	}
}
