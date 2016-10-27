package com.yauhenikuntsevich.training.onlinestore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public class TestGettingFromDB {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");

		// Test getting item from BD, table 'order_item'
		EntityDao<OrderItem> orderItemDao = (EntityDao<OrderItem>) context.getBean("orderItemDao");

		Long idOrderItem = 3L;

		System.out.println("Test query to BD in table 'order_item': \n" + orderItemDao.get(idOrderItem).getId() + " "
				+ orderItemDao.get(idOrderItem).getProduct().getName() + " "
				+ orderItemDao.get(idOrderItem).getProduct().getCategory().getItem() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getId() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getClient().getFirstName() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getAdministrator().getFirstName() + " "
				+ orderItemDao.get(idOrderItem).getQuantity() + " \n");

		// Test getting item from BD, table 'order'
		EntityDao<Order> orderDao = (EntityDao<Order>) context.getBean("orderDao");

		Long idOrder = 3L;

		System.out.println("Test query to BD in table 'order': \n" + orderDao.get(idOrder).getId() + " "
				+ orderDao.get(idOrder).getDateOrder() + " " + orderDao.get(idOrder).getClient().getFirstName() + " "
				+ orderDao.get(idOrder).getAdministrator().getFirstName() + " "
				+ orderDao.get(idOrder).getPriceAllPurchases() + "\n");

		// Test getting item from BD, table 'product'
		EntityDao<Product> productDao = (EntityDao<Product>) context.getBean("productDao");

		Long idProduct = 9L;

		System.out.println("Test query to BD in table 'product': \n" + productDao.get(idProduct).getId() + " "
				+ productDao.get(idProduct).getCategory().getItem() + " " + productDao.get(idProduct).getName() + " "
				+ productDao.get(idProduct).getPrice() + " " + productDao.get(idProduct).getQuantity() + "\n");

		// Test getting item from BD, table 'client'
		EntityDao<Client> сlientDao = (EntityDao<Client>) context.getBean("clientDao");

		Long idClient = 4L;

		System.out.println("Test query to BD in table 'client': \n" + сlientDao.get(idClient).getId() + " "
				+ сlientDao.get(idClient).getFirstName() + " " + сlientDao.get(idClient).getLastName() + " "
				+ сlientDao.get(idClient).getAge() + " " + сlientDao.get(idClient).getBlacklisted() + "\n");

		// Test getting item from BD, table 'administrator'
		EntityDao<Administrator> administratorDao = (EntityDao<Administrator>) context.getBean("administratorDao");

		Long idAdministrator = 2L;

		System.out
				.println("Test query to BD in table 'administrator': \n" + administratorDao.get(idAdministrator).getId()
						+ " " + administratorDao.get(idAdministrator).getFirstName() + " "
						+ administratorDao.get(idAdministrator).getLastName() + "\n");

		// Test getting item from BD, table 'category'
		EntityDao<Category> categoryDao = (EntityDao<Category>) context.getBean("categoryDao");

		Long idCategory = 3L;

		System.out.println("Test query to BD in table 'category': \n" + categoryDao.get(idCategory).getId() + " "
				+ categoryDao.get(idCategory).getItem() + "\n");
	}
}
