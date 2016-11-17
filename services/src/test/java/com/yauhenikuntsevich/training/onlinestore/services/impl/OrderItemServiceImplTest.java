package com.yauhenikuntsevich.training.onlinestore.services.impl;

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
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.exception.NotEnoughQuantityProductException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class OrderItemServiceImplTest {
	@Inject
	private OrderItemServiceImpl orderItemServiceImpl;
	@Inject
	private EntityDao<Product> productDao;

	@Inject
	private EntityDao<OrderItem> orderItemDao;

	OrderItem orderItem1;
	OrderItem orderItem2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		Order order1 = new Order();
		order1.setId(1L);

		Product product1 = new Product();
		product1.setId(2L);

		orderItem1 = new OrderItem();
		orderItem1.setOrder(order1);
		orderItem1.setProduct(product1);
		orderItem1.setQuantity(3);

		Order order2 = new Order();
		order2.setId(2L);

		Product product2 = new Product();
		product2.setId(8L);

		orderItem2 = new OrderItem();
		orderItem2.setOrder(order2);
		orderItem2.setProduct(product2);
		orderItem2.setQuantity(2);

		id1 = orderItemDao.add(orderItem1);
		id2 = orderItemDao.add(orderItem2);
	}

	@After
	public void afterTest() {
		orderItemDao.delete(id1);
		orderItemDao.delete(id2);
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
		orderItem1.setId(id1);

		// get product for recovery bd
		Product product3 = productDao.get(orderItem2.getProduct().getId());
		orderItemDao.delete(id2);

		Long id1Resave = orderItemServiceImpl.save(orderItem1);
		id2 = orderItemServiceImpl.save(orderItem2);

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		OrderItem orderItemFromDb1Resave = orderItemServiceImpl.get(id1Resave);
		OrderItem orderItemFromDb2 = orderItemServiceImpl.get(id2);

		Assert.assertEquals(orderItem1.getOrder().getId(), orderItemFromDb1Resave.getOrder().getId());
		Assert.assertEquals(orderItem1.getProduct().getId(), orderItemFromDb1Resave.getProduct().getId());
		Assert.assertEquals(orderItem1.getQuantity(), orderItemFromDb1Resave.getQuantity());

		Assert.assertEquals(orderItem2.getOrder().getId(), orderItemFromDb2.getOrder().getId());
		Assert.assertEquals(orderItem2.getProduct().getId(), orderItemFromDb2.getProduct().getId());
		Assert.assertEquals(orderItem2.getQuantity(), orderItemFromDb2.getQuantity());

		productDao.update(product3);
	}

	@Test
	public void saveAllTest() {
		orderItem1.setId(id1);

		List<OrderItem> orderItems1 = new LinkedList<>();
		orderItems1.add(orderItem1);
		orderItems1.add(orderItem2);

		// get product for recovery bd
		Product product3 = productDao.get(orderItem2.getProduct().getId());

		// liberation variable
		orderItemDao.delete(id2);

		int amountRowBeforeSaving = orderItemDao.getAll().size();

		List<OrderItem> orderItems = orderItemServiceImpl.saveAll(orderItems1);

		Long id1Resave = orderItems.get(0).getId();
		id2 = orderItems.get(1).getId();

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = orderItemDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		OrderItem orderItemFromDb1Resave = orderItemServiceImpl.get(id1Resave);
		OrderItem orderItemFromDb2 = orderItemServiceImpl.get(id2);

		Assert.assertEquals(orderItem1.getOrder().getId(), orderItemFromDb1Resave.getOrder().getId());
		Assert.assertEquals(orderItem1.getProduct().getId(), orderItemFromDb1Resave.getProduct().getId());
		Assert.assertEquals(orderItem1.getQuantity(), orderItemFromDb1Resave.getQuantity());

		Assert.assertEquals(orderItem2.getOrder().getId(), orderItemFromDb2.getOrder().getId());
		Assert.assertEquals(orderItem2.getProduct().getId(), orderItemFromDb2.getProduct().getId());
		Assert.assertEquals(orderItem2.getQuantity(), orderItemFromDb2.getQuantity());

		// liberation variable
		orderItemDao.delete(id1Resave);
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
		Product productBefore = productDao.get(orderItem1.getProduct().getId());
		Integer quantityProductBefore = productBefore.getQuantity();
		orderItemServiceImpl.subtractionQuantityForAdd(orderItem1);
		Product productAfter = productDao.get(orderItem1.getProduct().getId());
		Integer quantityProductAfter = productAfter.getQuantity();

		Assert.assertTrue(quantityProductBefore == quantityProductAfter + orderItem1.getQuantity());

		productAfter.setQuantity(quantityProductBefore);
		productDao.update(productAfter);
	}

	@Test
	public void substractionQuantityForUpdateTest() {
		Product productBefore = productDao.get(orderItem1.getProduct().getId());
		Integer quantityProductBefore = productBefore.getQuantity();
		orderItem1.setId(7L);
		orderItemServiceImpl.substractionQuantityForUpdate(orderItem1);
		Product productAfter = productDao.get(orderItem1.getProduct().getId());
		Integer quantityProductAfter = productAfter.getQuantity();

		Assert.assertTrue(quantityProductBefore == quantityProductAfter
				+ (orderItemServiceImpl.get(orderItem1.getId()).getQuantity() - orderItem1.getQuantity()));

		productAfter.setQuantity(quantityProductBefore);
		productDao.update(productAfter);
	}

	@Test(expected = NotEnoughQuantityProductException.class)
	public void checkQuantityTest() {
		orderItemServiceImpl.checkQuantity(5, 6);
	}
}
