package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.exception.NotEnoughQuantityProductException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class OrderItemServiceImplTest {
	@Inject
	private OrderItemServiceImpl orderItemServiceImpl;
	@Inject
	private EntityDao<Product> productDao;
	@Inject
	private EntityDao<Order> orderDao;
	@Inject
	private EntityDao<OrderItem> orderItemDao;

	OrderItem orderItem1;
	OrderItem orderItem2;
	OrderItem orderItem3;
	Product product1;
	Product product2;
	Product product3;
	Order order1;
	Order order3;
	Long id1;
	Long id2;
	Long id1Product;
	Long id2Product;
	Long id3Product;
	Long id1Order;
	Long id3Order;

	@Before
	public void beforeTest() {
		Administrator administrator1 = new Administrator();
		administrator1.setId(1L);

		Client client1 = new Client();
		client1.setId(1L);

		order1 = new Order();
		order1.setDateOrder(Date.valueOf("2015-10-09"));
		order1.setAdministrator(administrator1);
		order1.setClient(client1);
		order1.setPriceAllPurchases(400.0);
		id1Order = orderDao.add(order1);
		order1.setId(id1Order);

		Category category1 = new Category();
		category1.setId(1L);

		product1 = new Product();
		product1.setQuantity(25);
		product1.setCategory(category1);
		product1.setName("TestNameProduct1");
		product1.setPrice(138.8);
		id1Product = productDao.add(product1);
		product1.setId(id1Product);

		orderItem1 = new OrderItem();
		orderItem1.setOrder(order1);
		orderItem1.setProduct(product1);
		orderItem1.setQuantity(3);

		Order order2 = new Order();
		order2.setId(2L);

		Category category2 = new Category();
		category2.setId(2L);

		product2 = new Product();
		product2.setQuantity(13);
		product2.setCategory(category2);
		product2.setName("TestNameProduct2");
		product2.setPrice(99.3);
		id2Product = productDao.add(product2);
		product2.setId(id2Product);

		orderItem2 = new OrderItem();
		orderItem2.setOrder(order2);
		orderItem2.setProduct(product2);
		orderItem2.setQuantity(2);

		Administrator administrator3 = new Administrator();
		administrator3.setId(1L);

		Client client3 = new Client();
		client3.setId(1L);

		Order order3 = new Order();
		order3.setDateOrder(Date.valueOf("2015-05-12"));
		order3.setAdministrator(administrator3);
		order3.setClient(client3);
		order3.setPriceAllPurchases(299.0);
		id3Order = orderDao.add(order3);
		order3.setId(id3Order);

		Category category3 = new Category();
		category3.setId(3L);

		product3 = new Product();
		product3.setQuantity(85);
		product3.setCategory(category3);
		product3.setName("TestNameProduct3");
		product3.setPrice(56.7);
		id3Product = productDao.add(product3);
		product3.setId(id3Product);

		orderItem3 = new OrderItem();
		orderItem3.setOrder(order3);
		orderItem3.setProduct(product3);
		orderItem3.setQuantity(4);

		id1 = orderItemDao.add(orderItem1);
		id2 = orderItemDao.add(orderItem2);
	}

	@After
	public void afterTest() {
		orderItemServiceImpl.delete(id1);
		orderItemServiceImpl.delete(id2);
		productDao.delete(id1Product);
		productDao.delete(id2Product);
		productDao.delete(id3Product);
		orderDao.delete(id1Order);
		orderDao.delete(id3Order);
	}

	@Test
	public void getTest() {
		OrderItem orderItemFromDb1 = orderItemServiceImpl.get(id1);

		Assert.assertEquals(orderItem1.getOrder().getId(), orderItemFromDb1.getOrder().getId());
		Assert.assertEquals(orderItem1.getProduct().getId(), orderItemFromDb1.getProduct().getId());
		Assert.assertEquals(orderItem1.getQuantity(), orderItemFromDb1.getQuantity());
	}

	@Test
	public void getAllTest() {
		List<OrderItem> orderItems = orderItemServiceImpl.getAll();

		OrderItem orderItemFromDb1 = null;
		OrderItem orderItemFromDb2 = null;

		for (OrderItem orderItem : orderItems) {
			if (orderItem.getId().equals(id1))
				orderItemFromDb1 = orderItem;
			if (orderItem.getId().equals(id2))
				orderItemFromDb2 = orderItem;
		}

		Assert.assertEquals(orderItem1.getOrder().getId(), orderItemFromDb1.getOrder().getId());
		Assert.assertEquals(orderItem1.getProduct().getId(), orderItemFromDb1.getProduct().getId());
		Assert.assertEquals(orderItem1.getQuantity(), orderItemFromDb1.getQuantity());

		Assert.assertEquals(orderItem2.getOrder().getId(), orderItemFromDb2.getOrder().getId());
		Assert.assertEquals(orderItem2.getProduct().getId(), orderItemFromDb2.getProduct().getId());
		Assert.assertEquals(orderItem2.getQuantity(), orderItemFromDb2.getQuantity());
	}

	@Test
	public void saveTest() {
		orderItem3.setId(id1);

		// get product for recovery bd
		Product product3 = productDao.get(orderItem2.getProduct().getId());
		orderItemDao.delete(id2);
		orderItem2.setId(null);

		Long id1Updated = orderItemServiceImpl.save(orderItem3);
		id2 = orderItemServiceImpl.save(orderItem2);

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		OrderItem orderItemFromDb1 = orderItemServiceImpl.get(id1Updated);
		OrderItem orderItemFromDb2 = orderItemServiceImpl.get(id2);

		Assert.assertEquals(orderItem3.getOrder().getId(), orderItemFromDb1.getOrder().getId());
		Assert.assertEquals(orderItem3.getProduct().getId(), orderItemFromDb1.getProduct().getId());
		Assert.assertEquals(orderItem3.getQuantity(), orderItemFromDb1.getQuantity());

		Assert.assertEquals(orderItem2.getOrder().getId(), orderItemFromDb2.getOrder().getId());
		Assert.assertEquals(orderItem2.getProduct().getId(), orderItemFromDb2.getProduct().getId());
		Assert.assertEquals(orderItem2.getQuantity(), orderItemFromDb2.getQuantity());

		// recovery bd
		productDao.update(product3);
	}

	@Test
	public void saveAllTest() {
		orderItem3.setId(id1);

		List<OrderItem> orderItems1 = new LinkedList<>();
		orderItems1.add(orderItem3);
		orderItems1.add(orderItem2);

		// get product for recovery bd
		Product product3 = productDao.get(orderItem2.getProduct().getId());

		// liberation variable
		orderItemDao.delete(id2);
		orderItem2.setId(null);

		int amountRowBeforeSaving = orderItemDao.getAll().size();

		List<OrderItem> orderItems = orderItemServiceImpl.saveAll(orderItems1);

		Long id1Updated = orderItems.get(0).getId();
		id2 = orderItems.get(1).getId();

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = orderItemDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		OrderItem orderItemFromDb1 = orderItemServiceImpl.get(id1Updated);
		OrderItem orderItemFromDb2 = orderItemServiceImpl.get(id2);

		Assert.assertEquals(orderItem3.getOrder().getId(), orderItemFromDb1.getOrder().getId());
		Assert.assertEquals(orderItem3.getProduct().getId(), orderItemFromDb1.getProduct().getId());
		Assert.assertEquals(orderItem3.getQuantity(), orderItemFromDb1.getQuantity());

		Assert.assertEquals(orderItem2.getOrder().getId(), orderItemFromDb2.getOrder().getId());
		Assert.assertEquals(orderItem2.getProduct().getId(), orderItemFromDb2.getProduct().getId());
		Assert.assertEquals(orderItem2.getQuantity(), orderItemFromDb2.getQuantity());

		// liberation variable
		orderItemDao.delete(id1Updated);
		productDao.update(product3);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = orderItemDao.getAll().size();

		Boolean IsDeleted = orderItemServiceImpl.delete(id1);

		int amountRowAfterSaving = orderItemDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}

	@Test
	public void getProductsOneOrderTest() {
		List<Product> products = orderItemServiceImpl.getProductsOneOrder(1L);

		Assert.assertFalse(products.isEmpty());

		for (Product product : products) {
			if (orderItem1.getProduct().getId().equals(product.getId())) {
				Assert.assertTrue(true);
			}
		}
	}

	@Test
	public void subtractionQuantityTest() {
		Product productBefore = productDao.get(id1Product);
		Integer quantityProductBefore = productBefore.getQuantity();
		orderItemServiceImpl.subtractionQuantityFromProductAdding(orderItem1);
		Product productAfter = productDao.get(id1Product);
		Integer quantityProductAfter = productAfter.getQuantity();

		Assert.assertTrue(quantityProductBefore == quantityProductAfter + orderItem1.getQuantity());

		productAfter.setQuantity(quantityProductBefore);
		productDao.update(productAfter);
	}

	@Test
	public void substractionQuantityForUpdateTest() {
		Product productBefore = productDao.get(id1Product);
		Integer quantityProductBefore = productBefore.getQuantity();
		orderItem1.setId(id1);
		orderItem1.setQuantity(5);
		orderItemServiceImpl.substractionQuantityFromProductUpdating(orderItem1);
		Product productAfter = productDao.get(id1Product);
		Integer quantityProductAfter = productAfter.getQuantity();

		Assert.assertTrue(quantityProductBefore == quantityProductAfter
				+ (orderItem1.getQuantity() - orderItemServiceImpl.get(orderItem1.getId()).getQuantity()));

		productAfter.setQuantity(quantityProductBefore);
		productDao.update(productAfter);
	}

	@Test
	public void addPriceProductsToPriceAllPurchachesTest() {
		Order orderBefore = orderDao.get(id1Order);
		Double priceAllPurchachesBefore = orderBefore.getPriceAllPurchases();

		orderItemServiceImpl.addPriceProductsToPriceAllPurchaches(orderItem1);

		Order orderAfter = orderDao.get(id1Order);
		Double priceAllPurchachesAfter = orderAfter.getPriceAllPurchases();

		Assert.assertEquals(
				(Double) (priceAllPurchachesBefore + orderItem1.getQuantity() * orderItem1.getProduct().getPrice()),
				priceAllPurchachesAfter);
	}

	@Test(expected = NotEnoughQuantityProductException.class)
	public void checkQuantityTest() {
		orderItemServiceImpl.checkQuantity(5, 6);
	}
}
