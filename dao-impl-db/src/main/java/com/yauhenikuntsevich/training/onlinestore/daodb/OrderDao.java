package com.yauhenikuntsevich.training.onlinestore.daodb;

import java.util.List;

public interface OrderDao {
	OrderDao get(Long id);

	void save(OrderDao entity);

	void update(OrderDao entity);

	void delete(Long id);

	List<OrderDao> getAll();
}
