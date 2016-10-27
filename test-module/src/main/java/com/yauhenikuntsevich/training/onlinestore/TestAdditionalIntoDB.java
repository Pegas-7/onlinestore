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

public class TestAdditionalIntoDB {
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("service-context.xml");

		// Test additional item into BD, table 'order_item'
		EntityDao<OrderItem> orderItemDao = (EntityDao<OrderItem>) context.getBean("orderItemDao");

		Long idOrderItem = 3L;

		System.out.println("Test query to BD in table 'order_item': \n" + orderItemDao.get(idOrderItem).getId() + " "
				+ orderItemDao.get(idOrderItem).getProduct().getName() + " "
				+ orderItemDao.get(idOrderItem).getProduct().getCategory().getItem() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getId() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getClient().getFirstName() + " "
				+ orderItemDao.get(idOrderItem).getOrder().getAdministrator().getFirstName() + " "
				+ orderItemDao.get(idOrderItem).getQuantity() + " \n");

		// Test additional item into BD, table 'order'
		EntityDao<Order> orderDao = (EntityDao<Order>) context.getBean("orderDao");

		Long idOrder = 3L;

		System.out.println("Test query to BD in table 'order': \n" + orderDao.get(idOrder).getId() + " "
				+ orderDao.get(idOrder).getDateOrder() + " " + orderDao.get(idOrder).getClient().getFirstName() + " "
				+ orderDao.get(idOrder).getAdministrator().getFirstName() + " "
				+ orderDao.get(idOrder).getPriceAllPurchases() + "\n");

		// Test additional item into BD, table 'product'
		EntityDao<Product> productDao = (EntityDao<Product>) context.getBean("productDao");

		Category category1 = new Category();
		category1.setId(2l);

		Product product = new Product();
		product.setCategory(category1);
		product.setName("Samsung Duos 2");
		product.setPrice(221);
		product.setQuantity(19);

		System.out.println("New product was added with id: " + productDao.add(product) + "\n");

		// Test additional item into BD, table 'client'
		EntityDao<Client> сlientDao = (EntityDao<Client>) context.getBean("clientDao");

		Client client = new Client();
		client.setFirstName("Grisha_client");
		client.setLastName("Klop");
		client.setAge(23);
		client.setBlacklisted(true);

		System.out.println("New client was added with id: "+ сlientDao.add(client) + "\n");

		// Test additional item into BD, table 'administrator'
		EntityDao<Administrator> administratorDao = (EntityDao<Administrator>) context.getBean("administratorDao");

		Administrator administrator = new Administrator();
		administrator.setFirstName("Leonid_admin");
		administrator.setLastName("Pushnoy");
		
		System.out.println("New administrator was added with id: "+ administratorDao.add(administrator) + "\n");

		// Test additional item into BD, table 'category'
		EntityDao<Category> categoryDao = (EntityDao<Category>) context.getBean("categoryDao");

		Category category = new Category();
		category.setItem("Earphones");

		System.out.println("New category was added with id: "+ categoryDao.add(category) + "\n");
	}
}
