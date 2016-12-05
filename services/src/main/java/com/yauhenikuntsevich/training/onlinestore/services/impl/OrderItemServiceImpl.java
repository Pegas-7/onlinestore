package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Order;
import com.yauhenikuntsevich.training.onlinestore.datamodel.OrderItem;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;
import com.yauhenikuntsevich.training.onlinestore.services.OrderItemService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.OrderItemCaching;
import com.yauhenikuntsevich.training.onlinestore.services.exception.NotEnoughQuantityProductException;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheOrderItem;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Inject
	private EntityDao<OrderItem> orderItemDao;
	@Inject
	private EntityDao<Product> productDao;
	@Inject
	private EntityDao<Order> orderDao;

	public OrderItemCaching orderItemCaching = ExternalizableCacheOrderItem.createInstanceOrderItemCaching();

	@Override
	public List<OrderItem> saveAll(List<OrderItem> orderItems) {
		List<OrderItem> orderItems1 = new LinkedList<>();

		for (OrderItem orderItem2 : orderItems) {
			Long id = save(orderItem2);
			orderItem2.setId(id);
			orderItems1.add(orderItem2);
		}

		return orderItems1;
	}

	@Override
	public Long save(OrderItem orderItem) {
		if (orderItem.getId() == null) {
			subtractionQuantityFromProductAdding(orderItem);

			addPriceProductsToPriceAllPurchachesAdding(orderItem);

			return orderItemDao.add(orderItem);
		} else {
			substractionQuantityFromProductUpdating(orderItem);

			addPriceProductsToPriceAllPurchachesUpdating(orderItem);

			orderItemDao.update(orderItem);
			orderItemCaching.putInCache(orderItem.getId(), orderItem);
			return orderItem.getId();
		}
	}

	@Override
	public OrderItem get(Long id) {
		OrderItem orderItem = null;

		if (orderItemCaching.getCache().get(id) != null) {
			orderItem = orderItemCaching.getCache().get(id);
		} else {
			orderItem = orderItemDao.get(id);
			orderItemCaching.putInCache(id, orderItem);
		}

		return orderItem;
	}

	@Override
	public List<OrderItem> getAll() {
		return orderItemDao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		orderItemDao.delete(id);
		orderItemCaching.deleteFromCache(id);
		return true;
	}

	@Override
	public List<Product> getProductsOneOrder(Long orderId) {
		List<OrderItem> orderItems = orderItemDao.getAll();
		List<Product> productsForReturn = new LinkedList<>();

		for (OrderItem orderItem : orderItems) {
			if (orderItem.getOrder().getId() == orderId) {
				productsForReturn.add(orderItem.getProduct());
			}
		}

		return productsForReturn;
	}

	protected void subtractionQuantityFromProductAdding(OrderItem orderItem) {

		Long idProduct = orderItem.getProduct().getId();
		Product product = productDao.get(idProduct);

		Integer quantityContainsProduct = product.getQuantityStore();
		Integer quantityContainsOrderItem = orderItem.getQuantity();

		checkQuantity(quantityContainsProduct, quantityContainsOrderItem);

		Integer diffQuantity = quantityContainsProduct - quantityContainsOrderItem;
		product.setQuantityStore(diffQuantity);
		productDao.update(product);
	}

	protected void substractionQuantityFromProductUpdating(OrderItem orderItem) {
		Long idProduct = orderItem.getProduct().getId();
		Product product = productDao.get(idProduct);

		OrderItem orderItemFromDb = orderItemDao.get(orderItem.getId());

		Integer quantityContainsOrderItemFromDb = orderItemFromDb.getQuantity();
		Integer quantityContainsProduct = product.getQuantityStore();
		Integer quantityContainsOrderItem = orderItem.getQuantity();

		Integer addedProduct = quantityContainsOrderItem - quantityContainsOrderItemFromDb;
		checkQuantity(quantityContainsProduct, addedProduct);
		Integer updatedProductQuantity = quantityContainsProduct - addedProduct;

		product.setQuantityStore(updatedProductQuantity);
		productDao.update(product);
	}

	protected void addPriceProductsToPriceAllPurchachesAdding(OrderItem orderItem) {
		Order order = orderDao.get(orderItem.getOrder().getId());
		Double priceAllPurchaches = order.getPriceAllPurchases();

		Product product = productDao.get(orderItem.getProduct().getId());
		Double priceProducts = orderItem.getQuantity() * product.getPrice();

		Double priceAllPurchachesUpdated = priceAllPurchaches + priceProducts;

		order.setPriceAllPurchases(priceAllPurchachesUpdated);
		orderDao.update(order);
	}

	protected void addPriceProductsToPriceAllPurchachesUpdating(OrderItem orderItem) {
		Order order = orderDao.get(orderItem.getOrder().getId());
		Double priceAllPurchaches = order.getPriceAllPurchases();

		Product product = productDao.get(orderItem.getProduct().getId());

		Integer quantityInOrderItemFromDB = orderItemDao.get(orderItem.getId()).getQuantity();

		Double priceProducts = (orderItem.getQuantity() - quantityInOrderItemFromDB) * product.getPrice();

		Double priceAllPurchachesUpdated = priceAllPurchaches + priceProducts;

		order.setPriceAllPurchases(priceAllPurchachesUpdated);
		orderDao.update(order);
	}

	protected void checkQuantity(Integer quantityProduct, Integer quantityOrderItem) {
		if (quantityProduct - quantityOrderItem < 0) {
			throw new NotEnoughQuantityProductException();
		}
	}

	@Override
	public List<OrderItem> get(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}
}
