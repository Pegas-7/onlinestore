package com.yauhenikuntsevich.training.onlinestore;

import java.sql.Date;

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

		// Test adding, updating, deleting, table 'order_item'
		EntityDao<OrderItem> orderItemDao = (EntityDao<OrderItem>) context.getBean("orderItemDao");

		Order order1 = new Order();
		order1.setId(3L);

		Product product1 = new Product();
		product1.setId(5L);

		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order1);
		orderItem.setProduct(product1);
		orderItem.setQuantity(13345);

		Long orderItemId = orderItemDao.add(orderItem);
		orderItem.setId(orderItemId);

		System.out.println("New order_item " + orderItem.getOrder().getId() + " " + orderItem.getProduct().getId() + " "
				+ orderItem.getQuantity() + " was added with id: " + orderItemId + "\n");

		order1.setId(2L);
		product1.setId(4L);

		orderItem.setOrder(order1);
		orderItem.setProduct(product1);
		orderItem.setQuantity(15);

		orderItemDao.update(orderItem);

		System.out.println("Order " + orderItem.getOrder().getId() + " " + orderItem.getProduct().getId() + " "
				+ orderItem.getQuantity() + " was updated\n");

		orderItemDao.delete(orderItem.getId());

		System.out.println("OrderItem with id: " + orderItem.getId() + " was deleted\n");

		// Test adding, updating, deleting, table 'order'
		EntityDao<Order> orderDao = (EntityDao<Order>) context.getBean("orderDao");

		Client client1 = new Client();
		client1.setId(4L);

		Administrator administrator1 = new Administrator();
		administrator1.setId(2L);

		Order order = new Order();
		order.setDateOrder(Date.valueOf("1999-03-15"));
		order.setClient(client1);
		order.setAdministrator(administrator1);
		order.setPriceAllPurchases(67943545);

		Long orderId = orderDao.add(order);
		order.setId(orderId);

		System.out.println("New order " + order.getDateOrder() + " " + order.getClient().getId() + " "
				+ order.getAdministrator().getId() + " " + order.getPriceAllPurchases() + " was added with id: "
				+ orderId + "\n");

		client1.setId(1L);
		administrator1.setId(1L);

		order.setDateOrder(Date.valueOf("2016-03-15"));
		order.setClient(client1);
		order.setAdministrator(administrator1);
		order.setPriceAllPurchases(256);

		orderDao.update(order);

		System.out.println("Order " + order.getDateOrder() + " " + order.getClient().getId() + " "
				+ order.getAdministrator().getId() + " " + order.getPriceAllPurchases() + " was updated\n");

		orderDao.delete(order.getId());

		System.out.println("Order with id: " + order.getId() + " was deleted\n");

		// Test adding, updating, deleting, table 'product'
		EntityDao<Product> productDao = (EntityDao<Product>) context.getBean("productDao");

		Category category1 = new Category();
		category1.setId(2l);

		Product product = new Product();
		product.setCategory(category1);
		product.setName("NNamsung Duos 2");
		product.setPrice(221);
		product.setQuantity(19);

		Long productId = productDao.add(product);
		product.setId(productId);

		System.out.println("New product " + product.getCategory().getId() + " " + product.getName() + " "
				+ product.getPrice() + " " + product.getQuantity() + " was added with id: " + productId + "\n");

		category1.setId(3L);
		product.setCategory(category1);
		product.setName("Samsung Duos 2");
		product.setPrice(199);
		product.setQuantity(13);

		productDao.update(product);

		System.out.println("Product " + product.getCategory().getId() + " " + product.getName() + " "
				+ product.getPrice() + " " + product.getQuantity() + " was updated\n");

		productDao.delete(product.getId());

		System.out.println("Product with id: " + product.getId() + " was deleted\n");

		// Test adding, updating, deleting, table 'client'
		EntityDao<Client> сlientDao = (EntityDao<Client>) context.getBean("clientDao");

		Client client = new Client();
		client.setFirstName("Ggggrisha_client");
		client.setLastName("Klopppp");
		client.setAge(23666);
		client.setBlacklisted(false);

		Long clientId = сlientDao.add(client);
		client.setId(clientId);

		System.out.println("New client " + client.getFirstName() + " " + client.getLastName() + " " + client.getAge()
				+ " " + client.getBlacklisted() + " was added with id: " + clientId + "\n");

		client.setFirstName("Grisha_client");
		client.setLastName("Klop");
		client.setAge(23);
		client.setBlacklisted(true);

		сlientDao.update(client);

		System.out.println("Client " + client.getFirstName() + " " + client.getLastName() + " " + client.getAge() + " "
				+ client.getBlacklisted() + " was updated\n");

		сlientDao.delete(client.getId());

		System.out.println("Client with id: " + client.getId() + " was deleted\n");

		// Test adding, updating, deleting, table 'administrator'
		EntityDao<Administrator> administratorDao = (EntityDao<Administrator>) context.getBean("administratorDao");

		Administrator administrator = new Administrator();
		administrator.setFirstName("LLLeonid_admin");
		administrator.setLastName("Pushnoy");

		Long administratorId = administratorDao.add(administrator);
		administrator.setId(administratorId);

		System.out.println("New administrator " + administrator.getFirstName() + " " + administrator.getLastName()
				+ " was added with id: " + administratorId + "\n");

		administrator.setFirstName("Leonid_admin");
		administrator.setLastName("Pushnoy");

		administratorDao.update(administrator);

		System.out.println(
				"Administrator " + administrator.getFirstName() + " " + administrator.getLastName() + " was updated\n");

		administratorDao.delete(administrator.getId());

		System.out.println("Administrator with id: " + administrator.getId() + " was deleted\n");

		// Test adding, updating, deleting, table 'category'
		EntityDao<Category> categoryDao = (EntityDao<Category>) context.getBean("categoryDao");

		Category category = new Category();
		category.setItem("Eeeearphones");

		Long categoryId = categoryDao.add(category);
		category.setId(categoryId);

		System.out.println("New category " + category.getItem() + " was added with id: " + categoryId + "\n");

		category.setItem("Eearphones");

		categoryDao.update(category);

		System.out.println("Category " + category.getItem() + " was updated\n");

		categoryDao.delete(category.getId());

		System.out.println("Category with id: " + category.getId() + " was deleted\n");
	}
}
