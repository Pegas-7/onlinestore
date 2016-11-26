package com.yauhenikuntsevich.training.onlinestore.web.springsecurity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;

//@Component
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		List<Client> clients = new ArrayList<>();
		Client client = new Client();
		client.setFirstName("user");
		client.setPassword("password");
		client.setRole("USER");
		clients.add(client);
		client.setFirstName("user1");
		client.setPassword("password1");
		client.setRole("USER1");
		clients.add(client);

		for (int i = 0; i < clients.size(); i++) {
			auth.inMemoryAuthentication().withUser(clients.get(i).getFirstName()).password(clients.get(i).getPassword())
					.roles(clients.get(i).getRole());
		}
	}
}
