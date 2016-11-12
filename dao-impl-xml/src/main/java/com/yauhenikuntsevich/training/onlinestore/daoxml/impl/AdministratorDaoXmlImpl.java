package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@Repository
public class AdministratorDaoXmlImpl extends AbstractEntityDaoXml implements EntityDao<Administrator> {

	protected XStream xstream;
	protected File file;

	@Value("${basePath}")
	private String basePath;

	@PostConstruct
	private void intialize() throws IOException {
		// TODO move to the parent class
		// TODO refactoring: use classname instead of hardcoded filename
		xstream = new XStream();

		file = new File(basePath + "/" + AdministratorDaoXmlImpl.class.getSimpleName() + ".xml");
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

	public void update(Administrator entity) {
		List<Administrator> allAdministrators = readCollection();

		for (Administrator administrator2 : allAdministrators) {
			if (administrator2.getId().equals(entity.getId())) {
				administrator2.setFirstName(entity.getFirstName());
				administrator2.setLastName(entity.getLastName());
			}
		}

		writeCollection(allAdministrators);
	}

	public void delete(Long id) {
		List<Administrator> allAdministrators = readCollection();

		List<Administrator> newList = new ArrayList<>();
		// TODO: don't iterate whole collection
		for (Administrator administrator : allAdministrators) {
			if (!administrator.getId().equals(id)) {
				newList.add(administrator);
			}
		}
		writeCollection(newList);
	}

	public List<Administrator> getAll() {
		return readCollection();
	}

	private List<Administrator> readCollection() {
		return (List<Administrator>) xstream.fromXML(file);
	}

	private void writeCollection(List<Administrator> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);// TODO custom exception
		}
	}

	private Long getNextId(List<Administrator> allAdministrators) {
		return allAdministrators.size() == 0 ? 1l : allAdministrators.get(allAdministrators.size() - 1).getId() + 1;
	}
}
