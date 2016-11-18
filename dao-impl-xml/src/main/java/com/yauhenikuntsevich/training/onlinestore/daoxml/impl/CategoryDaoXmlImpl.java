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
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@Repository
public class CategoryDaoXmlImpl extends AbstractEntityDaoXml implements EntityDao<Category> {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(Category.class.getSimpleName(), Category.class);
		file = new File(basePath + "/" + Category.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	@Override
	public Category get(Long id) {
		List<Category> allCategories = readCollection();

		for (Category category : allCategories) {
			if (category.getId().equals(id)) {
				return category;
			}
		}
		return null;
	}

	@Override
	public Long add(Category category) {
		List<Category> allCategories = readCollection();
		Long id = getNextId(allCategories);
		category.setId(id);
		allCategories.add(category);
		writeCollection(allCategories);
		return id;
	}

	@Override
	public void update(Category category) {
		List<Category> allCategories = readCollection();

		for (Category category2 : allCategories) {
			if (category2.getId().equals(category.getId())) {
				category2.setItem(category.getItem());
				break;
			}
		}

		writeCollection(allCategories);
	}

	@Override
	public void delete(Long id) {
		List<Category> allCategories = readCollection();

		for (int i = 0; i < allCategories.size(); i++) {
			if (allCategories.get(i).getId().equals(id)) {
				allCategories.remove(i);
				break;
			}
		}
		writeCollection(allCategories);
	}

	@Override
	public List<Category> getAll() {
		return (List<Category>) readCollection();
	}

	private List<Category> readCollection() {
		return (List<Category>) xstream.fromXML(file);
	}

	private void writeCollection(List<Category> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<Category> allCategories) {
		return allCategories.size() == 0 ? 1l : allCategories.get(allCategories.size() - 1).getId() + 1;
	}
}