package com.yauhenikuntsevich.training.onlinestore.daoapi;

import java.util.List;

public interface EntityDao<T> {
	T get(Long id);

	Long add(T entity);

	Integer update(T entity);

	Integer delete(Long id);

	List<T> getAll();
}
