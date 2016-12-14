package com.yauhenikuntsevich.training.onlinestore.daoxml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.ProductDao;
import com.yauhenikuntsevich.training.onlinestore.daoxml.AbstractEntityDaoXml;
import com.yauhenikuntsevich.training.onlinestore.daoxml.exception.XmlFileNotFoundedException;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@Repository
public class ProductDaoXmlImpl extends AbstractEntityDaoXml implements ProductDao {

	@PostConstruct
	private void intialize() throws IOException {
		xstream.alias(Product.class.getSimpleName(), Product.class);
		file = new File(basePath + "/" + Product.class.getSimpleName() + "DataStorage.xml");

		if (!file.exists()) {
			file.createNewFile();
			xstream.toXML(new ArrayList<>(), new FileOutputStream(file));
		}
	}

	@Override
	public Product get(Long id) {
		List<Product> allProducts = readCollection();

		for (Product product : allProducts) {
			if (product.getId().equals(id)) {
				return product;
			}
		}
		return null;
	}

	@Override
	public Long add(Product product) {
		List<Product> allProducts = readCollection();
		Long id = getNextId(allProducts);
		product.setId(id);
		allProducts.add(product);
		writeCollection(allProducts);
		return id;
	}

	@Override
	public Integer update(Product product) {
		List<Product> allProducts = readCollection();

		for (Product product2 : allProducts) {
			if (product2.getId().equals(product.getId())) {
				product2.setCategory(product.getCategory());
				product2.setName(product.getName());
				product2.setPrice(product.getPrice());
				product2.setQuantityStore(product.getQuantityStore());
				break;
			}
		}

		writeCollection(allProducts);
		return 1;
	}

	@Override
	public Integer delete(Long id) {
		List<Product> allProducts = readCollection();

		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getId().equals(id)) {
				allProducts.remove(i);
				break;
			}
		}
		writeCollection(allProducts);
		return 1;
	}

	@Override
	public List<Product> getAll() {
		return (List<Product>) readCollection();
	}

	private List<Product> readCollection() {
		return (List<Product>) xstream.fromXML(file);
	}

	private void writeCollection(List<Product> newList) {
		try {
			xstream.toXML(newList, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlFileNotFoundedException();
		}
	}

	private Long getNextId(List<Product> allProducts) {
		return allProducts.size() == 0 ? 1l : allProducts.get(allProducts.size() - 1).getId() + 1;
	}

	@Override
	public List<Product> getAllProductsWithOneCategory(Long categoryId) {
		List<Product> allProducts = readCollection();
		List<Product> allProductsWithOneCategory = new ArrayList<>();

		for (Product product : allProducts) {
			if (product.getCategory().getId().equals(categoryId)) {
				allProductsWithOneCategory.add(product);
			}
		}
		return allProductsWithOneCategory;
	}
}
