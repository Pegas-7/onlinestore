package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class AdministratorCachingTest {

	@Inject
	private AdministratorCaching administratorCaching;

	Administrator administrator1;
	Administrator administrator2;
	Long id1 = 1L;
	Long id2 = 2L;

	@Before
	public void beforeTest() {
		administrator1 = new Administrator();
		administrator1.setFirstName("FirstNameAdministrator1");
		administrator1.setLastName("LastNameAdministrator1");

		administrator2 = new Administrator();
		administrator2.setFirstName("FirstNameAdministrator2");
		administrator2.setLastName("LastNameAdministrator2");
	}

	@After
	public void afterTest() {
		administratorCaching.setCache(new LinkedHashMap<>());
	}

	@Test
	public void putAdministratorInCacheTest() {
		administratorCaching.putInCache(id1, administrator1);
		administratorCaching.putInCache(id2, administrator2);

		Assert.assertEquals(administratorCaching.getCache().get(id1), administrator1);
		Assert.assertEquals(administratorCaching.getCache().get(id2), administrator2);
	}

	@Test
	public void deleteAdministratorFromCacheTest() {
		Map<Long, Administrator> map = new LinkedHashMap<>();
		map.put(id1, administrator1);
		map.put(id2, administrator2);
		administratorCaching.setCache(map);

		int mapSizeBefore = administratorCaching.getCache().size();

		administratorCaching.deleteFromCache(id1);
		administratorCaching.deleteFromCache(id2);

		int mapSizeAfter = administratorCaching.getCache().size();

		Assert.assertEquals(mapSizeBefore, mapSizeAfter + 2);
	}

	@Test
	public void cleanCacheTest() {
		Map<Long, Administrator> map = new LinkedHashMap<>();
		administratorCaching.setMinSizeCache(100L);
		administratorCaching.setMaxSizeCache(200L);

		// cache size < 'minSizeCache'
		map.put(new Long(1L), new Administrator());
		administratorCaching.setCache(map);

		int mapSizeBefore = administratorCaching.getCache().size();

		administratorCaching.cleanCache();

		int mapSizeAfter = administratorCaching.getCache().size();

		Assert.assertEquals(mapSizeBefore, mapSizeAfter);

		// 'minSizeCache' < cache size < 'maxSizeCache'
		for (long i = 2; i <= 199; i++) {
			map.put(new Long(i), new Administrator());
		}

		mapSizeBefore = administratorCaching.getCache().size();

		administratorCaching.cleanCache();

		mapSizeAfter = administratorCaching.getCache().size();

		Assert.assertEquals(mapSizeBefore, mapSizeAfter);

		// cache size >= 'maxSizeCache'
		map.put(new Long(200L), new Administrator());

		mapSizeBefore = administratorCaching.getCache().size();

		administratorCaching.cleanCache();

		mapSizeAfter = administratorCaching.getCache().size();

		Assert.assertEquals(administratorCaching.getMinSizeCache().intValue(), mapSizeAfter);
	}
}
