package com.yauhenikuntsevich.training.onlinestore.services.impl;

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
import com.yauhenikuntsevich.training.onlinestore.services.authentification.AuthenticationServiceClientImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AuthenticationServiceClientImplTest {

	@Inject
	private AuthenticationServiceClientImpl authenticationServiceClientImpl;

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
		client1.setPassword("password_user1");
		client1.setRole("USER_ROLE");

		client2 = new Client();
		client2.setFirstName("FirstNameClient2");
		client2.setLastName("LastNameClient2");
		client2.setAge(76);
		client2.setBlacklisted(false);
		client2.setPassword("password_user2");
		client2.setRole("USER_ROLE");

		id1 = clientDao.add(client1);
		id2 = clientDao.add(client2);
	}

	@After
	public void afterTest() {
		clientServiceImpl.delete(id1);
		clientServiceImpl.delete(id2);
	}

	@Test
	public void validateUserPasswordTest() {
		Assert.assertTrue(authenticationServiceClientImpl.validateUser(client1.getFirstName(), client1.getPassword(),
				client1.getRole()));
		Assert.assertTrue(authenticationServiceClientImpl.validateUser(client2.getFirstName(), client2.getPassword(),
				client2.getRole()));
	}
}
