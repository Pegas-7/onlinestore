package com.yauhenikuntsevich.training.onlinestore.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;
import com.yauhenikuntsevich.training.onlinestore.web.model.AdministratorModel;

@RestController
@RequestMapping("/administrators")

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
		Administrator administrator = administratorService.get(id);
		return new ResponseEntity<AdministratorModel>(
				conversionService.convert(administrator, AdministratorModel.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createNewAdministrator(@RequestBody AdministratorModel administratorModel) {
		administratorService.save(conversionService.convert(administratorModel, Administrator.class));
		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateAdministrator(@RequestBody AdministratorModel administratorModel,
			@PathVariable Long id) {
		Administrator administrator = conversionService.convert(administratorModel, Administrator.class);
		administrator.setId(id);
		administratorService.save(administrator);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		administratorService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
