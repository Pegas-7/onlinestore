package com.yauhenikuntsevich.training.onlinestore.services.caching;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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

	private Long maxSizeCache = 200L;
	private Long minSizeCache = 100L;
	private Map<String, PersonAbstractModel> cache = new LinkedHashMap<>();

	public AuthenticationServiceAdministratorCaching() {
	}

	public void putInCache(String firstName, PersonAbstractModel personAbstractModel) {
		cleanCache();
		cache.put(firstName, personAbstractModel);
		LOGGER.debug("PersonAbstractModel with firstName = " + firstName + " was put in cache, size cache (map) = "
				+ cache.size());
	}

	public void cleanCache() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<String, PersonAbstractModel>> iter = cache.entrySet().iterator();
			int i = 0;
			while (iter.hasNext()) {
				i++;
				iter.next();
				if (i > minSizeCache) {
					iter.remove();
				}
			}
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		maxSizeCache = in.readLong();
		minSizeCache = in.readLong();

		String firstNameExternal = "";
		PersonAbstractModel personAbstractModelExternal = null;

		while (in.available() > 0) {
			firstNameExternal = (String) in.readObject();
			personAbstractModelExternal = (PersonAbstractModel) in.readObject();

			this.cache.put(firstNameExternal, personAbstractModelExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(maxSizeCache);
		out.writeLong(minSizeCache);

		for (Map.Entry<String, PersonAbstractModel> pair : cache.entrySet()) {
			out.writeObject(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<String, PersonAbstractModel> getCache() {
		return cache;
	}

	public void setCache(Map<String, PersonAbstractModel> cache) {
		this.cache = cache;
	}

	public Long getMaxSizeCache() {
		return maxSizeCache;
	}

	public void setMaxSizeCache(Long maxSizeCache) {
		this.maxSizeCache = maxSizeCache;
	}

	public Long getMinSizeCache() {
		return minSizeCache;
	}

	public void setMinSizeCache(Long minSizeCache) {
		this.minSizeCache = minSizeCache;
	}
}
