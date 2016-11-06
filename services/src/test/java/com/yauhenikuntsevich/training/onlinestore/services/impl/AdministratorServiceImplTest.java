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

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AdministratorServiceImplTest {

	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	@Inject
	private EntityDao<Administrator> administratorDao;

	Administrator administrator1;
	Administrator administrator2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		administrator1 = new Administrator();
		administrator1.setFirstName("FirstNameAdministrator1");
		administrator1.setLastName("LastNameAdministrator1");

		administrator2 = new Administrator();
		administrator2.setFirstName("FirstNameAdministrator2");
		administrator2.setLastName("LastNameAdministrator2");

		id1 = administratorDao.add(administrator1);
		id2 = administratorDao.add(administrator2);
	}

	@After
	public void afterTest() {
		administratorDao.delete(id1);
		administratorDao.delete(id2);
	}

	@Test
	public void getTest() {
		Administrator administratorFromDb1 = administratorServiceImpl.get(id1);

		Assert.assertEquals(administrator1.getFirstName(), administratorFromDb1.getFirstName());
		Assert.assertEquals(administrator1.getLastName(), administratorFromDb1.getLastName());
	}

	@Test
	public void getAllTest() {
		List<Administrator> administrators = administratorServiceImpl.getAll();

		Administrator administratorFromDb1 = null;
		Administrator administratorFromDb2 = null;

		for (Administrator administrator : administrators) {
			if (administrator.getId().equals(id1))
				administratorFromDb1 = administrator;
			if (administrator.getId().equals(id2))
				administratorFromDb2 = administrator;
		}

		Assert.assertEquals(administrator1.getFirstName(), administratorFromDb1.getFirstName());
		Assert.assertEquals(administrator1.getLastName(), administratorFromDb1.getLastName());
		Assert.assertEquals(administrator2.getFirstName(), administratorFromDb2.getFirstName());
		Assert.assertEquals(administrator2.getLastName(), administratorFromDb2.getLastName());
	}

	@Test
	public void saveTest() {
		administrator1.setId(id1);

		administratorDao.delete(id2);

		Long id1Resave = administratorServiceImpl.save(administrator1);
		id2 = administratorServiceImpl.save(administrator2);

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		Administrator administratorFromDb1Resave = administratorServiceImpl.get(id1Resave);
		Administrator administratorFromDb2 = administratorServiceImpl.get(id2);

		Assert.assertEquals(administrator1.getFirstName(), administratorFromDb1Resave.getFirstName());
		Assert.assertEquals(administrator1.getLastName(), administratorFromDb1Resave.getLastName());
		Assert.assertEquals(administrator2.getFirstName(), administratorFromDb2.getFirstName());
		Assert.assertEquals(administrator2.getLastName(), administratorFromDb2.getLastName());
	}

	@Test
	public void saveAllTest() {
		administrator1.setId(id1);

		List<Administrator> administrators1 = new LinkedList<>();
		administrators1.add(administrator1);
		administrators1.add(administrator2);

		// liberation variable
		administratorDao.delete(id2);

		int amountRowBeforeSaving = administratorDao.getAll().size();

		List<Administrator> administrators = administratorServiceImpl.saveAll(administrators1);

		Long id1Resave = administrators.get(0).getId();
		id2 = administrators.get(1).getId();

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = administratorDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Administrator administratorFromDb1 = administratorServiceImpl.get(id1Resave);
		Administrator administratorFromDb2 = administratorServiceImpl.get(id2);

		Assert.assertEquals(administrator1.getFirstName(), administratorFromDb1.getFirstName());
		Assert.assertEquals(administrator1.getLastName(), administratorFromDb1.getLastName());
		Assert.assertEquals(administrator2.getFirstName(), administratorFromDb2.getFirstName());
		Assert.assertEquals(administrator2.getLastName(), administratorFromDb2.getLastName());

		// liberation variable
		administratorDao.delete(id1Resave);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = administratorDao.getAll().size();

		Boolean IsDeleted = administratorServiceImpl.delete(id1);

		int amountRowAfterSaving = administratorDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}
}
