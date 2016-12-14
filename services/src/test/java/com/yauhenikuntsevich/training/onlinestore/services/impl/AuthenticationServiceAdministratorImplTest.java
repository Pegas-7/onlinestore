package com.yauhenikuntsevich.training.onlinestore.services.impl;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AdministratorDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.authentification.AuthenticationServiceAdministratorImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AuthenticationServiceAdministratorImplTest {

	@Inject
	private AuthenticationServiceAdministratorImpl authenticationServiceAdministratorImpl;

	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	@Inject
	private AdministratorDao administratorDao;

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
	}

	@After
	public void afterTest() {
		administratorServiceImpl.delete(id1);
		administratorServiceImpl.delete(id2);
	}

	@Test
	public void validateUserPasswordTest() {
		Assert.assertTrue(authenticationServiceAdministratorImpl.validateUser(administrator1.getFirstName(),
				administrator1.getPassword(), administrator1.getRole()));
		Assert.assertTrue(authenticationServiceAdministratorImpl.validateUser(administrator2.getFirstName(),
				administrator2.getPassword(), administrator2.getRole()));
	}
}
