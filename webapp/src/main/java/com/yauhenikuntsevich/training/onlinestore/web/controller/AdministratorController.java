package com.yauhenikuntsevich.training.onlinestore.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.AdministratorService;
import com.yauhenikuntsevich.training.onlinestore.web.model.AdministratorModel;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

	@Inject
	private AdministratorService administratorService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AdministratorModel>> getAll() {
		List<Administrator> all = administratorService.getAll();
		List<AdministratorModel> converted = new ArrayList<>();
		for (Administrator administrator : all) {
			converted.add(administrator2model(administrator));
		}
		return new ResponseEntity<List<AdministratorModel>>(converted, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/{authorId}", method = RequestMethod.GET) public
	 * ResponseEntity<AdministratorModel> getById(@PathVariable Long authorId) {
	 * Author author = service.get(authorId); return new
	 * ResponseEntity<AdministratorModel>(entity2model(author), HttpStatus.OK);
	 * }
	 * 
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<Void>
	 * createNewAuthor(@RequestBody AdministratorModel authorModel) {
	 * service.create(model2entity(authorModel)); return new
	 * ResponseEntity<Void>(HttpStatus.CREATED);
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/{authorId}", method = RequestMethod.POST)
	 * public ResponseEntity<Void> updateAuthor(@RequestBody AdministratorModel
	 * authorModel, @PathVariable Long authorId) { Author author =
	 * model2entity(authorModel); author.setId(authorId);
	 * service.update(author); return new ResponseEntity<Void>(HttpStatus.OK);
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/{authorId}", method = RequestMethod.DELETE)
	 * public ResponseEntity<Void> delete(@PathVariable Long authorId) {
	 * service.delete(authorId); return new ResponseEntity<Void>(HttpStatus.OK);
	 * 
	 * }
	 */

	private AdministratorModel administrator2model(Administrator administrator) {
		AdministratorModel e = new AdministratorModel();
		e.setFirstName(administrator.getFirstName());
		e.setId(administrator.getId());
		e.setLastName(administrator.getLastName());
		return e;
	}

	private Administrator model2administrator(AdministratorModel administratorModel) {
		Administrator e = new Administrator();
		e.setFirstName(administratorModel.getFirstName());
		e.setId(administratorModel.getId());
		e.setLastName(administratorModel.getLastName());
		return e;
	}

}
