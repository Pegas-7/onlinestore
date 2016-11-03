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

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.util.MyLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class OrderServiceImplTest {
	@Inject
	private OrderServiceImpl orderServiceImpl;

	@Inject
	private EntityDao<Order> orderDao;

	Order order1;
	Order order2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		Administrator administrator1 = new Administrator();
		administrator1.setId(1L);

		Client client1 = new Client();
		client1.setId(2L);

		order1 = new Order();
		order1.setAdministrator(administrator1);
		order1.setClient(client1);
		order1.setDateOrder(Date.valueOf("2016-11-01"));
		order1.setPriceAllPurchases(500);

		Administrator administrator2 = new Administrator();
		administrator2.setId(2L);

		Client client2 = new Client();
		client2.setId(5L);

		order2 = new Order();
		order2.setAdministrator(administrator2);
		order2.setClient(client2);
		order2.setDateOrder(Date.valueOf("2015-11-01"));
		order2.setPriceAllPurchases(1000);

		id1 = orderDao.add(order1);
		id2 = orderDao.add(order2);
	}

	@After
	public void afterTest() {
		orderDao.delete(id1);
		orderDao.delete(id2);
	}

	@Test
	public void getTest() {
		Order orderFromDb1 = orderServiceImpl.get(id1);

		Assert.assertEquals(order1.getAdministrator().getId(), orderFromDb1.getAdministrator().getId());
		Assert.assertEquals(order1.getClient().getId(), orderFromDb1.getClient().getId());
		Assert.assertEquals(order1.getDateOrder(), orderFromDb1.getDateOrder());
		Assert.assertEquals(order1.getPriceAllPurchases(), orderFromDb1.getPriceAllPurchases());
	}

	@Test
	public void getAllTest() {
		List<Order> orders = orderServiceImpl.getAll();

		Order orderFromDb1 = null;
		Order orderFromDb2 = null;

		for (Order order : orders) {
			if (order.getId().equals(id1))
				orderFromDb1 = order;
			if (order.getId().equals(id2))
				orderFromDb2 = order;
		}

		Assert.assertEquals(order1.getAdministrator().getId(), orderFromDb1.getAdministrator().getId());
		Assert.assertEquals(order1.getClient().getId(), orderFromDb1.getClient().getId());
		Assert.assertEquals(order1.getDateOrder(), orderFromDb1.getDateOrder());
		Assert.assertEquals(order1.getPriceAllPurchases(), orderFromDb1.getPriceAllPurchases());

		Assert.assertEquals(order2.getAdministrator().getId(), orderFromDb2.getAdministrator().getId());
		Assert.assertEquals(order2.getClient().getId(), orderFromDb2.getClient().getId());
		Assert.assertEquals(order2.getDateOrder(), orderFromDb2.getDateOrder());
		Assert.assertEquals(order2.getPriceAllPurchases(), orderFromDb2.getPriceAllPurchases());
	}

	@Test
	public void saveTest() {
		order1.setId(id1);

		orderDao.delete(id2);

		Long id1Resave = orderServiceImpl.save(order1);
		id2 = orderServiceImpl.save(order2);

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		Order orderFromDb1Resave = orderServiceImpl.get(id1Resave);
		Order orderFromDb2 = orderServiceImpl.get(id2);

		Assert.assertEquals(order1.getAdministrator().getId(), orderFromDb1Resave.getAdministrator().getId());
		Assert.assertEquals(order1.getClient().getId(), orderFromDb1Resave.getClient().getId());
		Assert.assertEquals(order1.getDateOrder(), orderFromDb1Resave.getDateOrder());
		Assert.assertEquals(order1.getPriceAllPurchases(), orderFromDb1Resave.getPriceAllPurchases());

		Assert.assertEquals(order2.getAdministrator().getId(), orderFromDb2.getAdministrator().getId());
		Assert.assertEquals(order2.getClient().getId(), orderFromDb2.getClient().getId());
		Assert.assertEquals(order2.getDateOrder(), orderFromDb2.getDateOrder());
		Assert.assertEquals(order2.getPriceAllPurchases(), orderFromDb2.getPriceAllPurchases());
	}

	@Test
	public void saveAllTest() {
		order1.setId(id1);

		List<Order> orders1 = new LinkedList<>();
		orders1.add(order1);
		orders1.add(order2);

		// liberation variable
		orderDao.delete(id2);

		int amountRowBeforeSaving = orderDao.getAll().size();

		List<Order> orders = orderServiceImpl.saveAll(orders1);

		Long id1Resave = orders.get(0).getId();
		id2 = orders.get(1).getId();

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = orderDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Order orderFromDb1Resave = orderServiceImpl.get(id1Resave);
		Order orderFromDb2 = orderServiceImpl.get(id2);

		Assert.assertEquals(order1.getAdministrator().getId(), orderFromDb1Resave.getAdministrator().getId());
		Assert.assertEquals(order1.getClient().getId(), orderFromDb1Resave.getClient().getId());
		Assert.assertEquals(order1.getDateOrder(), orderFromDb1Resave.getDateOrder());
		Assert.assertEquals(order1.getPriceAllPurchases(), orderFromDb1Resave.getPriceAllPurchases());

		Assert.assertEquals(order2.getAdministrator().getId(), orderFromDb2.getAdministrator().getId());
		Assert.assertEquals(order2.getClient().getId(), orderFromDb2.getClient().getId());
		Assert.assertEquals(order2.getDateOrder(), orderFromDb2.getDateOrder());
		Assert.assertEquals(order2.getPriceAllPurchases(), orderFromDb2.getPriceAllPurchases());

		// liberation variable
		orderDao.delete(id1Resave);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = orderDao.getAll().size();

		Boolean IsDeleted = orderServiceImpl.delete(id1);

		int amountRowAfterSaving = orderDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}

	@Test
	public void getAllOrdersOneAdministrator() {
		List<Order> orders = orderServiceImpl.getAllOrdersOneAdministrator(1L);
		
		Assert.assertFalse(orders.isEmpty());

		for (Order order : orders) {
			Assert.assertEquals(order1.getAdministrator().getId(), order.getAdministrator().getId());
		}
	}

	@Test
	public void getAllOrdersOneClient() {
		List<Order> orders = orderServiceImpl.getAllOrdersOneClient(2L);

		Assert.assertFalse(orders.isEmpty());

		for (Order order : orders) {
			Assert.assertEquals(order1.getClient().getId(), order.getClient().getId());
		}
	}

	@Test
	public void getAllOrdersIntervalDate() {
		List<Order> orders = orderServiceImpl.getAllOrdersIntervalDate(Date.valueOf("2016-10-31"),
				Date.valueOf("2016-11-03"));

		Assert.assertFalse(orders.isEmpty());

		for (Order order : orders) {
			MyLogger.LOGGER.info("Date expected: " + order.getDateOrder() + "Date actual: " + order.getDateOrder());
			Assert.assertEquals(order1.getDateOrder(), order.getDateOrder());
		}
	}
}
