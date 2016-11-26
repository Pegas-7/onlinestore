package com.yauhenikuntsevich.training.onlinestore.web.springsecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

//@Component
//@EnableWebSecurity
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}
