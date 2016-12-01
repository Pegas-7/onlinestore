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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;
import com.yauhenikuntsevich.training.onlinestore.web.model.ClientModel;

@RestController
@RequestMapping("/admin/clients")

public class ClientController {
	@Inject
	ConversionService conversionService;

	@Inject
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClientModel>> getAll(
			@RequestParam(value = "blacklisted", defaultValue = "") String blacklisted) {
		List<ClientModel> converted = new ArrayList<>();

		checkBlacklisted(blacklisted, converted);

		return new ResponseEntity<List<ClientModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientModel> getById(@PathVariable Long id) {
		Client client = null;
		try {
			client = clientService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<ClientModel>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<ClientModel>(conversionService.convert(client, ClientModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewClient(@RequestBody ClientModel clientModel) {
		clientService.save(conversionService.convert(clientModel, Client.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateClient(@RequestBody ClientModel clientModel, @PathVariable Long id) {
		Client client = conversionService.convert(clientModel, Client.class);
		client.setId(id);
		clientService.save(client);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private void checkBlacklisted(String blacklisted, List<ClientModel> converted) {
		if (blacklisted.equals("true")) {
			List<Client> allClients = clientService.getAllClientBlacklisted(true);
			for (Client client : allClients) {
				converted.add(conversionService.convert(client, ClientModel.class));
			}
		} else {
			if (blacklisted.equals("false")) {
				List<Client> allClients = clientService.getAllClientBlacklisted(false);

				for (Client client : allClients) {
					converted.add(conversionService.convert(client, ClientModel.class));
				}
			} else {
				List<Client> allClients = clientService.getAll();

				for (Client client : allClients) {
					converted.add(conversionService.convert(client, ClientModel.class));
				}
			}
		}
	}
}
