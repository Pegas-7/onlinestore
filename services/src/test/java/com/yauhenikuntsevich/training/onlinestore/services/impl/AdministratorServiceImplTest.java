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

import com.yauhenikuntsevich.training.onlinestore.daoapi.AdministratorDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AdministratorServiceImplTest {

	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	@Inject
	private AdministratorDao administratorDao;

	Administrator administrator1;
	Administrator administrator2;
	Administrator administrator3;
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

		administrator3 = new Administrator();
		administrator3.setFirstName("FirstNameAdministrator3");
		administrator3.setLastName("LastNameAdministrator3");
		administrator3.setPassword("password_admin3");
		administrator3.setRole("ADMIN_ROLE");

		id1 = administratorDao.add(administrator1);
		id2 = administratorDao.add(administrator2);
	}

	@After
	public void afterTest() {
		administratorServiceImpl.delete(id1);
		administratorServiceImpl.delete(id2);
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
		administrator3.setId(id1);

		// liberation variable
		administratorDao.delete(id2);
		administrator2.setId(null);

		Long id1Updated = administratorServiceImpl.save(administrator3);
		id2 = administratorServiceImpl.save(administrator2);

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		Administrator administratorFromDb1 = administratorServiceImpl.get(id1Updated);
		Administrator administratorFromDb2 = administratorServiceImpl.get(id2);

		Assert.assertEquals(administrator3.getFirstName(), administratorFromDb1.getFirstName());
		Assert.assertEquals(administrator3.getLastName(), administratorFromDb1.getLastName());
		Assert.assertEquals(administrator2.getFirstName(), administratorFromDb2.getFirstName());
		Assert.assertEquals(administrator2.getLastName(), administratorFromDb2.getLastName());
	}

	@Test
	public void saveAllTest() {
		administrator3.setId(id1);

		List<Administrator> administrators1 = new LinkedList<>();
		administrators1.add(administrator3);
		administrators1.add(administrator2);

		// liberation variable
		administratorDao.delete(id2);
		administrator2.setId(null);

		int amountRowBeforeSaving = administratorDao.getAll().size();

		List<Administrator> administrators = administratorServiceImpl.saveAll(administrators1);

		Long id1Updated = administrators.get(0).getId();
		id2 = administrators.get(1).getId();

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = administratorDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Administrator administratorFromDb1 = administratorServiceImpl.get(id1Updated);
		Administrator administratorFromDb2 = administratorServiceImpl.get(id2);

		Assert.assertEquals(administrator3.getFirstName(), administratorFromDb1.getFirstName());
		Assert.assertEquals(administrator3.getLastName(), administratorFromDb1.getLastName());
		Assert.assertEquals(administrator2.getFirstName(), administratorFromDb2.getFirstName());
		Assert.assertEquals(administrator2.getLastName(), administratorFromDb2.getLastName());

		// liberation variable
		administratorServiceImpl.delete(id1Updated);
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
