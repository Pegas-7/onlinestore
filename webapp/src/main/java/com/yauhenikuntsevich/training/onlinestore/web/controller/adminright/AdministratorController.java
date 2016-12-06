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
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;
import com.yauhenikuntsevich.training.onlinestore.web.model.AdministratorModel;

@RestController
@RequestMapping("/admin/administrators")

public class AdministratorController {
	@Inject
	ConversionService conversionService;

	@Inject
	private AdministratorService administratorService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AdministratorModel>> getAll() {
		List<Administrator> allAdministrators = administratorService.getAll();
		List<AdministratorModel> converted = new ArrayList<>();
		for (Administrator administrator : allAdministrators) {
			converted.add(conversionService.convert(administrator, AdministratorModel.class));
		}

		return new ResponseEntity<List<AdministratorModel>>(converted, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AdministratorModel> getById(@PathVariable Long id) {
		Administrator administrator = null;
		try {
			administrator = administratorService.get(id);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<AdministratorModel>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AdministratorModel>(
				conversionService.convert(administrator, AdministratorModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createNewAdministrator(@RequestBody AdministratorModel administratorModel) {
		Long id;
		try {
			id = administratorService.save(conversionService.convert(administratorModel, Administrator.class));
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					"Incorrect data into request body. Perhaps have violations uniqueness data in database or "
							+ "sended entity with null fields",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<String>("Administrator was created in database with id = " + String.valueOf(id),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> updateAdministrator(@RequestBody AdministratorModel administratorModel,
			@PathVariable Long id) {
		Administrator administrator = conversionService.convert(administratorModel, Administrator.class);
		administrator.setId(id);

		try {
			Long id1 = administratorService.save(administrator);
			if (id1 != -1L) {
				return new ResponseEntity<String>("Administrator was updated in database", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Administrator with id = " + id + " not found in database",
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
		Boolean deleted = administratorService.delete(id);
		if (deleted) {
			return new ResponseEntity<String>("Administrator with id = " + id + " was deleted from database", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Administrator with id = " + id + " not found in database",
				HttpStatus.NOT_FOUND);
	}
}
