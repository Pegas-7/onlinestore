package com.yauhenikuntsevich.training.onlinestore.web.springsecurity;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//@Component
//@EnableWebSecurity
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}
