package com.yauhenikuntsevich.training.onlinestore.services.authentification;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public boolean validateUserPassword(String username, String password) {
		// TODO DAO query
		return username.equals("validuser") && password.equals("validpassword");
	}

}
