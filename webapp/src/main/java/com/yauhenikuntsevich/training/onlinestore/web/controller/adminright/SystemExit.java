package com.yauhenikuntsevich.training.onlinestore.web.controller.adminright;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/exit")

public class SystemExit {

	@RequestMapping(method = RequestMethod.GET)
	public void exit() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("service-context.xml");
		((AbstractApplicationContext) applicationContext).close();
		System.exit(0);
	}
}
