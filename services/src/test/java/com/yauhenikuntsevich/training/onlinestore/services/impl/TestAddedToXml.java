package com.yauhenikuntsevich.training.onlinestore.services.impl;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class TestAddedToXml {
	@Inject
	private EntityDao<Administrator> administratorDao;

	Administrator administrator1;
	Administrator administrator2;
	Long id1;
	Long id2;

	@Test
	public void saveTest() {
		administrator1 = new Administrator();
		administrator1.setFirstName("FirstNameAdministrator1");
		administrator1.setLastName("LastNameAdministrator1");

		administrator2 = new Administrator();
		administrator2.setFirstName("FirstNameAdministrator2");
		administrator2.setLastName("LastNameAdministrator2");

		id1 = administratorDao.add(administrator1);
		id2 = administratorDao.add(administrator2);
	}
}
