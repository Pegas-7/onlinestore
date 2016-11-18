package com.yauhenikuntsevich.training.onlinestore.services.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yauhenikuntsevich.training.onlinestore.daoapi.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class ProductServiceImplTest {
	@Inject
	private ProductServiceImpl productServiceImpl;

	@Inject
	private EntityDao<Product> productDao;

	Product product1;
	Product product2;
	Product product3;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		Category category1 = new Category();
		category1.setId(1L);

		product1 = new Product();
		product1.setName("Product1");
		product1.setCategory(category1);
		product1.setPrice(100.0);
		product1.setQuantity(5);

		Category category2 = new Category();
		category2.setId(2L);

		product2 = new Product();
		product2.setName("Product2");
		product2.setCategory(category2);
		product2.setPrice(200.0);
		product2.setQuantity(12);

		Category category3 = new Category();
		category3.setId(3L);

		product3 = new Product();
		product3.setName("Product3");
		product3.setCategory(category3);
		product3.setPrice(128.4);
		product3.setQuantity(24);

		id1 = productDao.add(product1);
		id2 = productDao.add(product2);
	}

	@After
	public void afterTest() {
		productServiceImpl.delete(id1);
		productServiceImpl.delete(id2);
	}

	@Test
	public void getTest() {
		Product productFromDb1 = productServiceImpl.get(id1);

		Assert.assertEquals(product1.getName(), productFromDb1.getName());
		Assert.assertEquals(product1.getCategory().getId(), productFromDb1.getCategory().getId());
		Assert.assertEquals(product1.getPrice(), productFromDb1.getPrice());
		Assert.assertEquals(product1.getQuantity(), productFromDb1.getQuantity());
	}

	@Test
	public void getAllTest() throws Exception {
		List<Product> products = productServiceImpl.getAll();

		Product productFromDb1 = null;
		Product productFromDb2 = null;

		for (Product product : products) {
			if (product.getId().equals(id1))
				productFromDb1 = product;
			if (product.getId().equals(id2))
				productFromDb2 = product;
		}

		Assert.assertEquals(product1.getName(), productFromDb1.getName());
		Assert.assertEquals(product1.getCategory().getId(), productFromDb1.getCategory().getId());
		Assert.assertEquals(product1.getPrice(), productFromDb1.getPrice());
		Assert.assertEquals(product1.getQuantity(), productFromDb1.getQuantity());

		Assert.assertEquals(product2.getName(), productFromDb2.getName());
		Assert.assertEquals(product2.getCategory().getId(), productFromDb2.getCategory().getId());
		Assert.assertEquals(product2.getPrice(), productFromDb2.getPrice());
		Assert.assertEquals(product2.getQuantity(), productFromDb2.getQuantity());
	}

	@Test
	public void saveTest() {
		product3.setId(id1);

		// liberation variable
		productDao.delete(id2);
		product2.setId(null);

		Long id1Updated = productServiceImpl.save(product3);
		id2 = productServiceImpl.save(product2);

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		Product productFromDb1 = productServiceImpl.get(id1Updated);
		Product productFromDb2 = productServiceImpl.get(id2);

		Assert.assertEquals(product3.getName(), productFromDb1.getName());
		Assert.assertEquals(product3.getCategory().getId(), productFromDb1.getCategory().getId());
		Assert.assertEquals(product3.getPrice(), productFromDb1.getPrice());
		Assert.assertEquals(product3.getQuantity(), productFromDb1.getQuantity());

		Assert.assertEquals(product2.getName(), productFromDb2.getName());
		Assert.assertEquals(product2.getCategory().getId(), productFromDb2.getCategory().getId());
		Assert.assertEquals(product2.getPrice(), productFromDb2.getPrice());
		Assert.assertEquals(product2.getQuantity(), productFromDb2.getQuantity());
	}

	@Test
	public void saveAllTest() {
		product3.setId(id1);

		List<Product> products1 = new LinkedList<>();
		products1.add(product3);
		products1.add(product2);

		// liberation variable
		productDao.delete(id2);
		product2.setId(null);

		int amountRowBeforeSaving = productDao.getAll().size();

		List<Product> products = productServiceImpl.saveAll(products1);

		Long id1Updated = products.get(0).getId();
		id2 = products.get(1).getId();

		Assert.assertEquals(id1, id1Updated);
		Assert.assertNotNull(id1Updated);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = productDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Product productFromDb1 = productServiceImpl.get(id1Updated);
		Product productFromDb2 = productServiceImpl.get(id2);

		Assert.assertEquals(product3.getName(), productFromDb1.getName());
		Assert.assertEquals(product3.getCategory().getId(), productFromDb1.getCategory().getId());
		Assert.assertEquals(product3.getPrice(), productFromDb1.getPrice());
		Assert.assertEquals(product3.getQuantity(), productFromDb1.getQuantity());

		Assert.assertEquals(product2.getName(), productFromDb2.getName());
		Assert.assertEquals(product2.getCategory().getId(), productFromDb2.getCategory().getId());
		Assert.assertEquals(product2.getPrice(), productFromDb2.getPrice());
		Assert.assertEquals(product2.getQuantity(), productFromDb2.getQuantity());

		// liberation variable
		productDao.delete(id1Updated);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = productDao.getAll().size();

		Boolean IsDeleted = productServiceImpl.delete(id1);

		int amountRowAfterSaving = productDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}

	@Test
	public void getAllProductsWithOneCategoryTest() {
		List<Product> products = productServiceImpl.getAllProductsWithOneCategory(1L);

		Assert.assertFalse(products.isEmpty());

		for (Product product : products) {
			Assert.assertEquals(product1.getCategory().getId(), product.getCategory().getId());
		}
	}
}
