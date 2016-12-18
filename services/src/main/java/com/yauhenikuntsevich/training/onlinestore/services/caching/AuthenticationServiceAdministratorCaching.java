package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

@Service
public class AuthenticationServiceAdministratorCaching implements Externalizable {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceAdministratorCaching.class);

	private Integer maxSizeCache = 200;
	private Integer minSizeCache = 100;
	private Map<String, PersonAbstractModel> cache = new LinkedHashMap<>();
	private Long delayCacheCleanig = 80000L;
	private Date dateCleaningCache = settingDateCleaningCache();

	public AuthenticationServiceAdministratorCaching() {
	}

	public void putInCache(String firstName, PersonAbstractModel personAbstractModel) {
		cleanCache();
		cache.put(firstName, personAbstractModel);
		LOGGER.debug("PersonAbstractModel(Administrator) with firstName = " + firstName
				+ " was put in cache, size cache (map) = " + cache.size());
	}

	public void cleanCache() {
		cleanCacheByVolume();
		cleanCacheByTime();
	}

	private void cleanCacheByVolume() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<String, PersonAbstractModel>> iter = cache.entrySet().iterator();
			int i = 0;
			while (iter.hasNext()) {
				i++;
				iter.next();
				if (i < minSizeCache) {
					iter.remove();
				}
			}
		}
		LOGGER.debug("Cache AuthenticationServiceAdministratorCaching was cleaning by volume , size cache (map) = "
				+ cache.size());
	}

	public void cleanCacheByTime() {
		Date date = new Date();

		if (date.after(dateCleaningCache)) {
			cache.clear();
			settingDateCleaningCache();

			LOGGER.debug(
					"Cache AuthenticationServiceAdministratorCaching was cleaning by time, set date next cleaning = "
							+ dateCleaningCache + ", size cache (map) = " + cache.size());
		}
	}

	public Date settingDateCleaningCache() {
		Date date = new Date();
		date.setTime(date.getTime() + delayCacheCleanig);
		dateCleaningCache = date;
		return date;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		maxSizeCache = in.readInt();
		minSizeCache = in.readInt();
		Integer sizeMap = in.readInt();

		String firstNameExternal = "";
		PersonAbstractModel personAbstractModelExternal = null;

		for (int i = 0; i < sizeMap; i++) {
			firstNameExternal = (String) in.readObject();
			personAbstractModelExternal = (PersonAbstractModel) in.readObject();

			this.cache.put(firstNameExternal, personAbstractModelExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(maxSizeCache);
		out.writeInt(minSizeCache);
		out.writeInt(cache.size());

		for (Map.Entry<String, PersonAbstractModel> pair : cache.entrySet()) {
			out.writeObject(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<String, PersonAbstractModel> getCache() {
		cleanCache();
		return cache;
	}

	public void setCache(Map<String, PersonAbstractModel> cache) {
		this.cache = cache;
	}

	public Integer getMaxSizeCache() {
		return maxSizeCache;
	}

	public void setMaxSizeCache(Integer maxSizeCache) {
		this.maxSizeCache = maxSizeCache;
	}

	public Integer getMinSizeCache() {
		return minSizeCache;
	}

	public void setMinSizeCache(Integer minSizeCache) {
		this.minSizeCache = minSizeCache;
	}

	public Long getDelayCacheCleanig() {
		return delayCacheCleanig;
	}

	public void setDelayCacheCleanig(Long delayCacheCleanig) {
		this.delayCacheCleanig = delayCacheCleanig;
	}
}
