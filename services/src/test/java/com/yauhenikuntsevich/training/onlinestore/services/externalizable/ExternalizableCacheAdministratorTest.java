package com.yauhenikuntsevich.training.onlinestore.services.externalizable;

import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.caching.AdministratorCaching;
import com.yauhenikuntsevich.training.onlinestore.services.impl.AdministratorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class ExternalizableCacheAdministratorTest {
	@Inject
	private AdministratorServiceImpl administratorServiceImpl;

	AdministratorCaching administratorCaching;
	Administrator administrator1;
	Administrator administrator2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		administratorCaching = administratorServiceImpl.administratorCaching;
		administrator1 = new Administrator();
		administrator1.setFirstName("FirstNameAdministrator1");
		administrator1.setLastName("LastNameAdministrator1");
		administrator1.setPassword("adminTEST");
		administrator1.setRole("ADMIN_ROLE_TEST");

		administrator2 = new Administrator();
		administrator2.setFirstName("FirstNameAdministrator2");
		administrator2.setLastName("LastNameAdministrator2");
		administrator2.setPassword("adminTEST");
		administrator2.setRole("ADMIN_ROLE_TEST");

		id1 = administratorServiceImpl.save(administrator1);
		id2 = administratorServiceImpl.save(administrator2);
	}

	@After
	public void afterTest() {
		administratorServiceImpl.delete(id1);
		administratorServiceImpl.delete(id2);
	}

	@Test
	public void writeInFileCacheAdministratorTest() throws FileNotFoundException {

		administratorServiceImpl.get(id1);
		administratorServiceImpl.get(id2);

		ExternalizableCacheAdministrator.writeCacheInFile(administratorCaching);

		AdministratorCaching administratorCachingRecreate = ExternalizableCacheAdministrator.readCacheFromFile();

		Assert.assertEquals(administratorCachingRecreate.getCache().get(id1).getFirstName(),
				administrator1.getFirstName());
		Assert.assertEquals(administratorCachingRecreate.getCache().get(id2).getFirstName(),
				administrator2.getFirstName());

		ExternalizableCacheAdministrator.deleteFile();
	}

	@Test
	public void readFromFileCacheAdministratorTest() throws FileNotFoundException {
		AdministratorCaching administratorCaching1 = new AdministratorCaching();

		HashMap<Long, Administrator> hashMap = new HashMap<Long, Administrator>();
		Administrator administrator3 = new Administrator();
		administrator3.setId(13L);
		administrator3.setFirstName("FirstNameAdministrator3");
		hashMap.put(13L, administrator3);

		administratorCaching1.setCache(hashMap);

		ExternalizableCacheAdministrator.writeCacheInFile(administratorCaching1);

		AdministratorCaching newAdministratorCaching = ExternalizableCacheAdministrator.readCacheFromFile();

		Assert.assertEquals(newAdministratorCaching.getCache().get(13L).getFirstName(), administrator3.getFirstName());
	}
}
