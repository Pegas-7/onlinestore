package com.yauhenikuntsevich.training.onlinestore.services;

public interface EntityCaching<T> {
	void putInCache(Long id, T entity);

	void deleteFromCache(Long id);

	void cleanCache();
}
