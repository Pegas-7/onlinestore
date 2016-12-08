package com.yauhenikuntsevich.training.onlinestore.web.controller.clientright;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.ClientService;
import com.yauhenikuntsevich.training.onlinestore.web.model.ClientModel;

@RestController
@RequestMapping("/client/clients")

public class ClientControllerClientRight {
	@Inject
	ConversionService conversionService;

	@Inject
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ClientModel> getOwnData(@RequestHeader(value = "Authorization") String authorization) {
		String firstName = getFirstNameFromHeader(authorization);
		Client client = null;
		try {
			client = clientService.get(clientService.getIdByFirstName(firstName));
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<ClientModel>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ClientModel>(conversionService.convert(client, ClientModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> updateOwnData(@RequestBody ClientModel clientModel,
			@RequestHeader(value = "Authorization") String authorization) {
		String firstName = getFirstNameFromHeader(authorization);
		Client client = conversionService.convert(clientModel, Client.class);
		client.setId(clientService.getIdByFirstName(firstName));
		client.setFirstName(firstName);

		try {
			Long id1 = clientService.save(client);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Client was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>(
						"Client with id = " + clientService.getIdByFirstName(firstName) + " not found in database",
						HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	private String getFirstNameFromHeader(String authorization) {
		try {
			String base64Credentials = authorization.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			return credentials.split(":", 2)[0];
		} catch (Exception e) {
			return null;
		}
	}
}
