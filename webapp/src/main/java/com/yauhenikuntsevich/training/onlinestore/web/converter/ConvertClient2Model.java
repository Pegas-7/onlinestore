package com.yauhenikuntsevich.training.onlinestore.web.converter;

import org.springframework.core.convert.converter.Converter;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.web.model.ClientModel;

public class ConvertClient2Model implements Converter<Client, ClientModel> {

	@Override
	public ClientModel convert(Client client) {
		ClientModel e = new ClientModel();
		e.setId(client.getId());
		e.setFirstName(client.getFirstName());
		e.setLastName(client.getLastName());
		e.setAge(client.getAge());
		e.setBlacklisted(client.getBlacklisted());
		e.setRole(client.getRole());
		return e;
	}
}
