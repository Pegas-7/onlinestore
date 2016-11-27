package com.yauhenikuntsevich.training.onlinestore.services.caching;

public interface EntityCaching<T> {
	void putInCache(Long id, T entity);

	void deleteFromCache(Long id);

	void cleanCache();
}
