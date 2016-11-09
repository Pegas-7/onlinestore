package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.util.Date;

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
import com.yauhenikuntsevich.training.onlinestore.services.impl.AdministratorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AdministratorCachingTest {

	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	@Inject
	private EntityDao<Administrator> administratorDao;

	@Inject
	private AdministratorCaching administratorCaching;

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
	public void putAdministratorInCacheTest() {
		Administrator administrator3 = new Administrator();
		administrator3.setFirstName("FirstNameAdministrator3");
		administrator3.setLastName("LastNameAdministrator3");

		Long id3 = administratorDao.add(administrator3);
		administratorCaching.putAdministratorInCache(id3, administrator3);
		Assert.assertTrue(administratorCaching.getCache().containsKey(id3));

	}

	@Test
	public void setDateCleaningCacheTest() {
		administratorCaching.setDelayCacheCleanig(10000L);

		Date date = administratorCaching.settingDateCleaningCache();

		Assert.assertEquals(administratorCaching.getDateCleaningCache(), date);
	}
}
