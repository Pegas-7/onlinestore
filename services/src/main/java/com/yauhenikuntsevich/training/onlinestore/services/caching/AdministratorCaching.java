package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.services.util.MyLogger;

@Component
public class AdministratorCaching implements Externalizable {

	private Long delayCacheCleanig = 10000L;
	private Map<Long, Administrator> cache = new HashMap<>();
	private Date dateCleaningCache = settingDateCleaningCache();

	public AdministratorCaching() {
	}

	public void putAdministratorInCache(Long id, Administrator administrator) {
		cleanCache();
		cache.put(id, administrator);
		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	public void updateAdministratorInCache(Long id, Administrator administrator) {
		cleanCache();
		cache.put(id, administrator);

		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was updated in cache, size cache (map) = " + cache.size());
	}

	public void deleteAdministratorFromCache(Long id) {
		cleanCache();
		cache.remove(id);

		MyLogger.LOGGER
				.debug("Administrator with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	public void cleanCache() {
		Date date = new Date();

		if (date.after(dateCleaningCache)) {
			cache.clear();
			settingDateCleaningCache();

			MyLogger.LOGGER.debug("Cache was cleaning, set date next cleaning = " + dateCleaningCache
					+ ", size cache (map) = " + cache.size());
		}
	}

	public Date settingDateCleaningCache() {
		Date date = new Date();
		date.setTime(date.getTime() + delayCacheCleanig);
		dateCleaningCache = date;
		return date;
	}

	public void setDelayCacheCleanig(Long delayCacheCleanig) {
		this.delayCacheCleanig = delayCacheCleanig;
	}

	public Map<Long, Administrator> getCache() {
		return cache;
	}

	public Date getDateCleaningCache() {
		return dateCleaningCache;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		dateCleaningCache = new Date(in.readLong());

		Long idExternal = 0L;
		Administrator administratorExternal = null;

		while (in.available() > 0) {
			idExternal = in.readLong();
			administratorExternal = (Administrator) in.readObject();

			this.cache.put(idExternal, administratorExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(dateCleaningCache.getTime());

		for (Map.Entry<Long, Administrator> pair : cache.entrySet()) {
			out.writeLong(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public void setCache(Map<Long, Administrator> cache) {
		this.cache = cache;
	}
}
