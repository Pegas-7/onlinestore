package com.yauhenikuntsevich.training.onlinestore.services;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("service-context.xml");

		String[] beanDefinitionNames = springContext.getBeanDefinitionNames();
		for (String beanName : beanDefinitionNames) {
			System.out.println(beanName);
		}

		OnlineStore onlineStoreBean = springContext.getBean(OnlineStore.class);
		System.out.println("OnlineStore exists:" + (onlineStoreBean != null ? true : false));

		System.out.println("OnlineStore.dao exists:" + (onlineStoreBean.isDaoExist() ? true : false));
	}
}
