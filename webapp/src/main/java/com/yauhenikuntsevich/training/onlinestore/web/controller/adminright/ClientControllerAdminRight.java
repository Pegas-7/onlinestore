package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
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

public class ClientControllerAdminRight {

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
			return new ResponseEntity<ClientModel>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ClientModel>(conversionService.convert(client, ClientModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewClient(@RequestBody ClientModel clientModel) {
		Long id;
		try {
			id = clientService.save(conversionService.convert(clientModel, Client.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Client was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateClient(@RequestBody ClientModel clientModel, @PathVariable Long id) {
		Client client = conversionService.convert(clientModel, Client.class);
		client.setId(id);

		try {
			Long id1 = clientService.save(client);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Client was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Client with id = " + id + " not found in database",
						HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Boolean deleted = clientService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("Client with id = " + id + " was deleted from database", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Client with id = " + id + " not found in database", HttpStatus.NOT_FOUND);
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
