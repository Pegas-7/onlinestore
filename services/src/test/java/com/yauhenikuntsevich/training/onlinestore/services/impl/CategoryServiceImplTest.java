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

import com.yauhenikuntsevich.training.onlinestore.daodb.EntityDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/service-context.xml" })
public class CategoryServiceImplTest {

	@Inject
	private CategoryServiceImpl categoryServiceImpl;

	@Inject
	private EntityDao<Category> categoryDao;

	Category category1;
	Category category2;
	Long id1;
	Long id2;

	@Before
	public void beforeTest() {
		category1 = new Category();
		category1.setItem("FirstItem");

		category2 = new Category();
		category2.setItem("SecondItem");

		id1 = categoryDao.add(category1);
		id2 = categoryDao.add(category2);
	}

	@After
	public void afterTest() {
		categoryDao.delete(id1);
		categoryDao.delete(id2);
	}

	@Test
	public void getTest() {
		Category categoryFromDb1 = categoryServiceImpl.get(id1);

		Assert.assertEquals(category1.getItem(), categoryFromDb1.getItem());
	}

	@Test
	public void getAllTest() throws Exception {
		List<Category> сategories = categoryServiceImpl.getAll();

		Category categoryFromDb1 = null;
		Category categoryFromDb2 = null;

		for (Category category : сategories) {
			if (category.getId().equals(id1))
				categoryFromDb1 = category;
			if (category.getId().equals(id2))
				categoryFromDb2 = category;
		}

		Assert.assertEquals(category1.getItem(), categoryFromDb1.getItem());
		Assert.assertEquals(category2.getItem(), categoryFromDb2.getItem());
	}

	@Test
	public void saveTest() {
		category1.setId(id1);

		categoryDao.delete(id2);

		Long id1Resave = categoryServiceImpl.save(category1);
		id2 = categoryServiceImpl.save(category2);

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		Category categoryFromDb1Resave = categoryServiceImpl.get(id1Resave);
		Category categoryFromDb2 = categoryServiceImpl.get(id2);

		Assert.assertEquals(category1.getItem(), categoryFromDb1Resave.getItem());
		Assert.assertEquals(category2.getItem(), categoryFromDb2.getItem());
	}

	@Test
	public void saveAllTest() {
		category1.setId(id1);

		List<Category> categories1 = new LinkedList<>();
		categories1.add(category1);
		categories1.add(category2);

		// liberation variable
		categoryDao.delete(id2);

		int amountRowBeforeSaving = categoryDao.getAll().size();

		List<Category> categories2 = categoryServiceImpl.saveAll(categories1);

		Long id1Resave = categories2.get(0).getId();
		id2 = categories2.get(1).getId();

		Assert.assertEquals(id1, id1Resave);
		Assert.assertNotNull(id1Resave);
		Assert.assertNotNull(id2);

		int amountRowAfterSaving = categoryDao.getAll().size();

		Assert.assertEquals(amountRowBeforeSaving + 1, amountRowAfterSaving);

		Category categoryFromDb1Resave = categoryServiceImpl.get(id1Resave);
		Category categoryFromDb2 = categoryServiceImpl.get(id2);

		Assert.assertEquals(category1.getItem(), categoryFromDb1Resave.getItem());
		Assert.assertEquals(category2.getItem(), categoryFromDb2.getItem());

		// liberation variable
		categoryDao.delete(id1Resave);
	}

	@Test
	public void deleteTest() {
		int amountRowBeforeSaving = categoryDao.getAll().size();

		Boolean IsDeleted = categoryServiceImpl.delete(id1);

		int amountRowAfterSaving = categoryDao.getAll().size();

		Assert.assertTrue(IsDeleted);
		Assert.assertEquals(amountRowBeforeSaving, amountRowAfterSaving + 1);
	}
}
