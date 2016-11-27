package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.daoxml.exception.XmlFileNotFoundedException;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@Repository
public class AdministratorDaoXmlImpl extends AbstractEntityDaoXml implements EntityDao<Administrator> {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(Administrator.class.getSimpleName(), Administrator.class);
		file = new File(basePath + "/" + Administrator.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	public Administrator get(Long id) {
		List<Administrator> allAdministrators = readCollection();

		for (Administrator administrator : allAdministrators) {
			if (administrator.getId().equals(id)) {
				return administrator;
			}
		}
		return null;
	}

	public Long add(Administrator administrator) {
		List<Administrator> allAdministrators = readCollection();
		Long id = getNextId(allAdministrators);
		administrator.setId(id);
		allAdministrators.add(administrator);
		writeCollection(allAdministrators);
		return id;
	}

	public void update(Administrator administrator) {
		List<Administrator> allAdministrators = readCollection();

		for (Administrator administrator2 : allAdministrators) {
			if (administrator2.getId().equals(administrator.getId())) {
				administrator2.setFirstName(administrator.getFirstName());
				administrator2.setLastName(administrator.getLastName());
				administrator2.setPassword(administrator.getPassword());
				administrator2.setRole(administrator.getRole());
				break;
			}
		}

		writeCollection(allAdministrators);
	}

	public void delete(Long id) {
		List<Administrator> allAdministrators = readCollection();

		for (int i = 0; i < allAdministrators.size(); i++) {
			if (allAdministrators.get(i).getId().equals(id)) {
				allAdministrators.remove(i);
				break;
			}
		}
		writeCollection(allAdministrators);
	}

	public List<Administrator> getAll() {
		return (List<Administrator>) readCollection();
	}

	private List<Administrator> readCollection() {
		return (List<Administrator>) xstream.fromXML(file);
	}

	private void writeCollection(List<Administrator> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<Administrator> allAdministrators) {
		return allAdministrators.size() == 0 ? 1l : allAdministrators.get(allAdministrators.size() - 1).getId() + 1;
	}
}
