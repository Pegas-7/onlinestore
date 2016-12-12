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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.services.EntityCaching;

@Service
public class ClientCaching implements Externalizable, EntityCaching<Client> {

	public static final Logger LOGGER = LoggerFactory.getLogger(ClientCaching.class);

	private Long maxSizeCache = 200L;
	private Long minSizeCache = 100L;
	private Map<Long, Client> cache = new LinkedHashMap<>();

	public ClientCaching() {
	}

	@Override
	public void putInCache(Long id, Client client) {
		cleanCache();
		cache.put(id, client);
		LOGGER.debug("Client with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	@Override
	public void deleteFromCache(Long id) {
		cleanCache();
		cache.remove(id);

		LOGGER.debug("Client with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	@Override
	public void cleanCache() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<Long, Client>> iter = cache.entrySet().iterator();
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
		Long idExternal = 0L;
		Client сlientExternal = null;

		while (in.available() > 0) {
			idExternal = in.readLong();
			сlientExternal = (Client) in.readObject();

			this.cache.put(idExternal, сlientExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		for (Map.Entry<Long, Client> pair : cache.entrySet()) {
			out.writeLong(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<Long, Client> getCache() {
		return cache;
	}

	public void setCache(Map<Long, Client> cache) {
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
