package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.util.MyLogger;

public class AdministratorCaching {

	private static Map<Long, Administrator> cache = new HashMap<>();
	private static Long delayCacheCleanig = 1000L;
	private static Date dateCleaningCache = setDateCleaningCache();

	public static void putAdministratorInCache(Long id, Administrator administrator) {
		cleanCache();
		cache.put(id, administrator);
		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	public static void updateAdministratorInCache(Long id, Administrator administrator) {
		cleanCache();
		cache.put(id, administrator);

		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was updated in cache, size cache (map) = " + cache.size());
	}

	public static void deleteAdministratorFromCache(Long id) {
		cleanCache();
		cache.remove(id);
		cache.remove(1000);

		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	public static void cleanCache() {
		Date date = new Date();

		if (date.after(dateCleaningCache)) {
			cache.clear();
			setDateCleaningCache();

			MyLogger.LOGGER.debug("Cache was cleaning, set date next cleaning = " + dateCleaningCache
					+ ", size cache (map) = " + cache.size());
		}
	}

	public static Date setDateCleaningCache() {
		Date date = new Date();
		date.setTime(date.getTime() + delayCacheCleanig);
		dateCleaningCache = date;
		return date;
	}

	public static void setDelayCacheCleanig(Long delayCacheCleanig) {
		AdministratorCaching.delayCacheCleanig = delayCacheCleanig;
	}

	public static Map<Long, Administrator> getCache() {
		return cache;
	}
}
