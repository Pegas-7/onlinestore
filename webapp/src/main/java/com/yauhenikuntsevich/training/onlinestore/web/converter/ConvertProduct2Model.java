package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.web.model.AdministratorModel;

public class ConvertProduct2Model implements Converter<Administrator, AdministratorModel> {

	@Override
	public AdministratorModel convert(Administrator administrator) {
		AdministratorModel e = new AdministratorModel();
		e.setId(administrator.getId());
		e.setFirstName(administrator.getFirstName());
		e.setLastName(administrator.getLastName());
		e.setRole(administrator.getRole());
		return e;
	}
}
