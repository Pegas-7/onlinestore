package com.yauhenikuntsevich.training.onlinestore.services;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Product;

public class SpringScopeTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("service-context.xml");

		System.out.println(springContext.getBean(Product.class).hashCode());
		System.out.println(springContext.getBean(Product.class).hashCode());
	}
}
