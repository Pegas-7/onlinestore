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
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.authentification.AuthenticationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AuthenticationServiceImplTest {

	@Inject
	private AuthenticationServiceImpl authenticationServiceImpl;

	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	@Inject
	private EntityDao<Administrator> administratorDao;

	@Inject
	private ClientServiceImpl clientServiceImpl;

	@Inject
	private EntityDao<Client> clientDao;

	Client client1;
	Client client2;
	Long id3;
	Long id4;

	Administrator administrator1;
	Administrator administrator2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		administrator1 = new Administrator();
		administrator1.setFirstName("FirstNameAdministrator1");
		administrator1.setLastName("LastNameAdministrator1");
		administrator1.setPassword("password_admin1");
		administrator1.setRole("ADMIN_ROLE");

		administrator2 = new Administrator();
		administrator2.setFirstName("FirstNameAdministrator2");
		administrator2.setLastName("LastNameAdministrator2");
		administrator2.setPassword("password_admin2");
		administrator2.setRole("ADMIN_ROLE");

		id1 = administratorDao.add(administrator1);
		id2 = administratorDao.add(administrator2);

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

		id3 = clientDao.add(client1);
		id4 = clientDao.add(client2);
	}

	@After
	public void afterTest() {
		administratorServiceImpl.delete(id1);
		administratorServiceImpl.delete(id2);
		clientServiceImpl.delete(id3);
		clientServiceImpl.delete(id4);
	}

	@Test
	public void validateUserPasswordTest() {
		Assert.assertTrue(authenticationServiceImpl.validateUser(administrator1.getFirstName(),
				administrator1.getPassword(), administrator1.getRole()));
		Assert.assertTrue(authenticationServiceImpl.validateUser(administrator2.getFirstName(),
				administrator2.getPassword(), administrator2.getRole()));
		Assert.assertTrue(authenticationServiceImpl.validateUser(client1.getFirstName(), client1.getPassword(),
				client1.getRole()));
		Assert.assertTrue(authenticationServiceImpl.validateUser(client2.getFirstName(), client2.getPassword(),
				client2.getRole()));

	}
}
