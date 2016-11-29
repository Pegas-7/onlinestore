package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.web.model.ClientModel;

public class ConvertModel2Client implements Converter<ClientModel, Client> {

	@Override
	public Client convert(ClientModel clientModel) {
		Client e = new Client();
		e.setFirstName(clientModel.getFirstName());
		e.setLastName(clientModel.getLastName());
		e.setAge(clientModel.getAge());
		e.setBlacklisted(clientModel.getBlacklisted());
		e.setPassword(clientModel.getPassword());
		e.setRole(clientModel.getRole());
		return e;
	}
}
