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
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class ClientServiceImplTest {

	@Inject
	private ClientServiceImpl clientServiceImpl;

	@Inject
	private EntityDao<Client> clientDao;

	Client client1;
	Client client2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		client1 = new Client();
		client1.setFirstName("FirstNameClient1");
		client1.setLastName("LastNameClient1");
		client1.setAge(18);
		client1.setBlacklisted(true);

		client2 = new Client();
		client2.setFirstName("FirstNameClient2");
		client2.setLastName("LastNameClient2");
		client2.setAge(76);
		client2.setBlacklisted(false);

		id1 = clientDao.add(client1);
		id2 = clientDao.add(client2);
	}

	@After
	public void afterTest() {
		clientDao.delete(id1);
		clientDao.delete(id2);
	}

	@Test
	public void getTest() {
		Client clientFromDb1 = clientServiceImpl.get(id1);

		Assert.assertEquals(client1.getFirstName(), clientFromDb1.getFirstName());
		Assert.assertEquals(client1.getLastName(), clientFromDb1.getLastName());
		Assert.assertEquals(client1.getAge(), clientFromDb1.getAge());
		Assert.assertEquals(client1.getBlacklisted(), clientFromDb1.getBlacklisted());
	}

	@Test
	public void getAllTest() throws Exception {
		List<Client> сlients = clientServiceImpl.getAll();

		Client clientFromDb1 = null;
		Client clientFromDb2 = null;

		for (Client сlient : сlients) {
			if (сlient.getId().equals(id1))
				clientFromDb1 = сlient;
			if (сlient.getId().equals(id2))
				clientFromDb2 = сlient;
		}

		Assert.assertEquals(client1.getFirstName(), clientFromDb1.getFirstName());
		Assert.assertEquals(client1.getLastName(), clientFromDb1.getLastName());
		Assert.assertEquals(client1.getAge(), clientFromDb1.getAge());
		Assert.assertEquals(client1.getBlacklisted(), clientFromDb1.getBlacklisted());

		Assert.assertEquals(client2.getFirstName(), clientFromDb2.getFirstName());
		Assert.assertEquals(client2.getLastName(), clientFromDb2.getLastName());
		Assert.assertEquals(client2.getAge(), clientFromDb2.getAge());
		Assert.assertEquals(client2.getBlacklisted(), clientFromDb2.getBlacklisted());
	}

	@Test
	public void saveTest() {
		client1.setId(id1);

		clientDao.delete(id2);

		Long id1Resave = clientServiceImpl.save(client1);
		id2 = clientServiceImpl.save(client2);

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		Client clientFromDb1Resave = clientServiceImpl.get(id1Resave);
		Client clientFromDb2 = clientServiceImpl.get(id2);

		Assert.assertEquals(client1.getFirstName(), clientFromDb1Resave.getFirstName());
		Assert.assertEquals(client1.getLastName(), clientFromDb1Resave.getLastName());
		Assert.assertEquals(client1.getAge(), clientFromDb1Resave.getAge());
		Assert.assertEquals(client1.getBlacklisted(), clientFromDb1Resave.getBlacklisted());

		Assert.assertEquals(client2.getFirstName(), clientFromDb2.getFirstName());
		Assert.assertEquals(client2.getLastName(), clientFromDb2.getLastName());
		Assert.assertEquals(client2.getAge(), clientFromDb2.getAge());
		Assert.assertEquals(client2.getBlacklisted(), clientFromDb2.getBlacklisted());
	}

	@Test
	public void saveAllTest() {
		client1.setId(id1);

		List<Client> clients1 = new LinkedList<>();
		clients1.add(client1);
		clients1.add(client2);

		// liberation variable
		clientDao.delete(id2);

		int amountRowBeforeSaving = clientDao.getAll().size();

		List<Client> clients = clientServiceImpl.saveAll(clients1);

		Long id1Resave = clients.get(0).getId();
		id2 = clients.get(1).getId();

		int amountRowAfterSaving = clientDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Client clientFromDb1Resave = clientServiceImpl.get(id1Resave);
		Client clientFromDb2 = clientServiceImpl.get(id2);

		Assert.assertEquals(client1.getFirstName(), clientFromDb1Resave.getFirstName());
		Assert.assertEquals(client1.getLastName(), clientFromDb1Resave.getLastName());
		Assert.assertEquals(client1.getAge(), clientFromDb1Resave.getAge());
		Assert.assertEquals(client1.getBlacklisted(), clientFromDb1Resave.getBlacklisted());

		Assert.assertEquals(client2.getFirstName(), clientFromDb2.getFirstName());
		Assert.assertEquals(client2.getLastName(), clientFromDb2.getLastName());
		Assert.assertEquals(client2.getAge(), clientFromDb2.getAge());
		Assert.assertEquals(client2.getBlacklisted(), clientFromDb2.getBlacklisted());

		// liberation variable
		clientDao.delete(id1Resave);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = clientDao.getAll().size();

		Boolean IsDeleted = clientServiceImpl.delete(id1);

		int amountRowAfterSaving = clientDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}

	@Test
	public void getAllClientBlacklistedTest() {
		List<Client> clientsBlacklistedTrue = clientServiceImpl.getAllClientBlacklisted(true);

		Assert.assertFalse(clientsBlacklistedTrue.isEmpty());

		for (Client client : clientsBlacklistedTrue) {
			Assert.assertTrue(client.getBlacklisted());
		}

		List<Client> clientsBlacklistedFalse = clientServiceImpl.getAllClientBlacklisted(false);

		Assert.assertFalse(clientsBlacklistedFalse.isEmpty());

		for (Client client : clientsBlacklistedFalse) {
			Assert.assertFalse(client.getBlacklisted());
		}
	}
}
