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

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.EntityCaching;

@Service
public class ProductCaching implements Externalizable, EntityCaching<Product> {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProductCaching.class);

	private Long maxSizeCache = 200L;
	private Long minSizeCache = 100L;
	private Map<Long, Product> cache = new LinkedHashMap<>();

	public ProductCaching() {
	}

	@Override
	public void putInCache(Long id, Product product) {
		cleanCache();
		cache.put(id, product);
		LOGGER.debug("Product with id = " + id + " was put in cache, size cache (map) = " + cache.size());
	}

	@Override
	public void deleteFromCache(Long id) {
		cleanCache();
		cache.remove(id);

		LOGGER.debug("Product with id = " + id + " was deleted from cache, size cache (map) = " + cache.size());
	}

	@Override
	public void cleanCache() {
		if (cache.size() >= maxSizeCache) {
			Iterator<Map.Entry<Long, Product>> iter = cache.entrySet().iterator();
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

		Long idExternal = 0L;
		Product productExternal = null;

		while (in.available() > 0) {
			idExternal = in.readLong();
			productExternal = (Product) in.readObject();

			this.cache.put(idExternal, productExternal);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(maxSizeCache);
		out.writeLong(minSizeCache);

		for (Map.Entry<Long, Product> pair : cache.entrySet()) {
			out.writeLong(pair.getKey());
			out.writeObject(pair.getValue());
		}
	}

	public Map<Long, Product> getCache() {
		return cache;
	}

	public void setCache(Map<Long, Product> cache) {
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
