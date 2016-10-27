package com.yauhenikuntsevich.training.onlinestore;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public class TestGettingAllFromDB {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");

		// Test getting all items from BD, table 'order_item'
		EntityDao<OrderItem> orderItemDao = (EntityDao<OrderItem>) context.getBean("orderItemDao");

		List<OrderItem> orderItems = orderItemDao.getAll();

		System.out.println("Test getting all items from BD, table 'orderItem':");

		for (OrderItem orderItem : orderItems) {
			System.out.println(orderItem.getId() + " " + orderItem.getProduct().getName() + " "
					+ orderItem.getProduct().getCategory().getItem() + " " + orderItem.getOrder().getId() + " "
					+ orderItem.getOrder().getClient().getFirstName() + " "
					+ orderItem.getOrder().getAdministrator().getFirstName() + " " + orderItem.getQuantity());
		}
		System.out.println();

		// Test getting all items from BD, table 'order'
		EntityDao<Order> orderDao = (EntityDao<Order>) context.getBean("orderDao");

		List<Order> orders = orderDao.getAll();

		System.out.println("Test getting all items from BD, table 'order':");

		for (Order order : orders) {
			System.out.println(order.getId() + " " + order.getDateOrder() + " " + order.getClient().getFirstName() + " "
					+ order.getAdministrator().getFirstName() + " " + order.getPriceAllPurchases());
		}
		System.out.println();

		// Test getting all items from BD, table 'product'
		EntityDao<Product> productDao = (EntityDao<Product>) context.getBean("productDao");

		List<Product> products = productDao.getAll();

		System.out.println("Test getting all items from BD, table 'product':");

		for (Product product : products) {
			System.out.println(product.getId() + " " + product.getCategory().getItem() + " " + product.getName() + " "
					+ product.getPrice() + product.getQuantity());
		}
		System.out.println();

		// Test getting all items from BD, table 'client'
		EntityDao<Client> сlientDao = (EntityDao<Client>) context.getBean("clientDao");

		List<Client> clients = сlientDao.getAll();

		System.out.println("Test getting all items from BD, table 'сlient':");

		for (Client client : clients) {
			System.out.println(client.getId() + " " + client.getFirstName() + " " + client.getLastName() + " "
					+ client.getAge() + " " + client.getBlacklisted());
		}
		System.out.println();

		// Test getting all items from BD, table 'administrator'
		EntityDao<Administrator> administratorDao = (EntityDao<Administrator>) context.getBean("administratorDao");

		List<Administrator> administrators = administratorDao.getAll();

		System.out.println("Test getting all items from BD, table 'administrator':");

		for (Administrator administrator : administrators) {
			System.out.println(
					administrator.getId() + " " + administrator.getFirstName() + " " + administrator.getLastName());
		}
		System.out.println();

		// Test getting all items from BD, table 'category'
		EntityDao<Category> categoryDao = (EntityDao<Category>) context.getBean("categoryDao");

		List<Category> categories = categoryDao.getAll();

		System.out.println("Test getting all items from BD, table 'category':");

		for (Category category : categories) {
			System.out.println(category.getId() + " " + category.getItem());
		}
		System.out.println();
	}
}
