package com.yauhenikuntsevich.training.onlinestore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yauhenikuntsevich.training.onlinestore.daodb.AdministratorDao;
import com.yauhenikuntsevich.training.onlinestore.daodb.CategoryDao;

public class Test {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");

		// Test query to BD in table 'administrator'
		AdministratorDao administratorDao = (AdministratorDao) context.getBean("administratorDao");

		System.out.println(administratorDao.get((long) 1).getId() + " " + administratorDao.get((long) 1).getFirstName()
				+ " " + administratorDao.get((long) 1).getLastName() + "\n");

		// Test query to BD in table 'category'
		CategoryDao categoryDao = (CategoryDao) context.getBean("categoryDao");

		System.out.println(categoryDao.get((long) 1).getId() + " " + categoryDao.get((long) 1).getItem() + "\n");
	}
}
