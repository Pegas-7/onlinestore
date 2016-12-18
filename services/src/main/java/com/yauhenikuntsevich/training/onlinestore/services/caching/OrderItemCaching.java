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

import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.services.EntityCaching;

@Service
public class OrderItemCaching implements Externalizable, EntityCaching<OrderItem> {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderItemCaching.class);

	private Long maxSizeCache = 200L;
	private Long minSizeCache = 100L;
	private Map<Long, OrderItem> cache = new LinkedHashMap<>();

	public OrderItemCaching() {
	}

	@Override
	public void putInCache(Long id, OrderItem orderItem) {
		cleanCache();
		cache.put(id, orderItem);
		LOGGER.debug("OrderItem with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	@Override
	public void deleteFromCache(Long id) {
		cleanCache();
		cache.remove(id);

		LOGGER.debug("OrderItem with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	@Override
	public void cleanCache() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<Long, OrderItem>> iter = cache.entrySet().iterator();
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
		OrderItem orderItemExternal = null;

		while (in.available() > 0) {
			idExternal = in.readLong();
			orderItemExternal = (OrderItem) in.readObject();

			this.cache.put(idExternal, orderItemExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(maxSizeCache);
		out.writeLong(minSizeCache);

		for (Map.Entry<Long, OrderItem> pair : cache.entrySet()) {
			out.writeLong(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<Long, OrderItem> getCache() {
		return cache;
	}

	public void setCache(Map<Long, OrderItem> cache) {
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
