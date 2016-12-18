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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.services.EntityCaching;

@Service
public class OrderCaching implements Externalizable, EntityCaching<Order> {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderCaching.class);

	private Long maxSizeCache = 200L;
	private Long minSizeCache = 100L;
	private Map<Long, Order> cache = new LinkedHashMap<>();

	public OrderCaching() {
	}

	@Override
	public void putInCache(Long id, Order order) {
		cleanCache();
		cache.put(id, order);
		LOGGER.debug("Order with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	@Override
	public void deleteFromCache(Long id) {
		cleanCache();
		cache.remove(id);

		LOGGER.debug("Order with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	@Override
	public void cleanCache() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<Long, Order>> iter = cache.entrySet().iterator();
			int i = 0;
			while (iter.hasNext()) {
				i++;
				iter.next();
				if (i < minSizeCache) {
					iter.remove();
				}
			}
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		maxSizeCache = in.readLong();
		minSizeCache = in.readLong();

		Long idExternal = 0L;
		Order orderExternal = null;

		while (in.available() > 0) {
			idExternal = in.readLong();
			orderExternal = (Order) in.readObject();

			this.cache.put(idExternal, orderExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(maxSizeCache);
		out.writeLong(minSizeCache);

		for (Map.Entry<Long, Order> pair : cache.entrySet()) {
			out.writeLong(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<Long, Order> getCache() {
		return cache;
	}

	public void setCache(Map<Long, Order> cache) {
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
