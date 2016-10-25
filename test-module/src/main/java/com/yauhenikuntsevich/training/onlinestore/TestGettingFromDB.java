package com.yauhenikuntsevich.training.onlinestore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yauhenikuntsevich.training.onlinestore.daodb.AdministratorDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.CategoryDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.ClientDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.ProductDao;

public class TestGettingFromDB {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");

		// Test query to BD in table 'product'
		ProductDao productDao = (ProductDao) context.getBean("productDao");

		Long idProduct = 9L;

		System.out.println(productDao.get(idProduct).getId() + " " + productDao.get(idProduct).getCategory() + " "
				+ productDao.get(idProduct).getName() + " " + productDao.get(idProduct).getPrice() + " "
				+ productDao.get(idProduct).getQuantity() + "\n");

		// Test query to BD in table 'client'
		ClientDao сlientDao = (ClientDao) context.getBean("clientDao");

		Long idClient = 4L;

		System.out.println(сlientDao.get(idClient).getId() + " " + сlientDao.get(idClient).getFirstName() + " "
				+ сlientDao.get(idClient).getLastName() + " " + сlientDao.get(idClient).getAge() + " "
				+ сlientDao.get(idClient).getBlacklisted() + "\n");

		// Test query to BD in table 'administrator'
		AdministratorDao administratorDao = (AdministratorDao) context.getBean("administratorDao");

		Long idAdministrator = 2L;

		System.out.println(administratorDao.get(idAdministrator).getId() + " "
				+ administratorDao.get(idAdministrator).getFirstName() + " "
				+ administratorDao.get(idAdministrator).getLastName() + "\n");

		// Test query to BD in table 'category'
		CategoryDao categoryDao = (CategoryDao) context.getBean("categoryDao");

		Long idCategory = 3L;

		System.out.println(categoryDao.get(idCategory).getId() + " " + categoryDao.get(idCategory).getItem() + "\n");
	}
}
