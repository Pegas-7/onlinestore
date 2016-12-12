package com.yauhenikuntsevich.training.onlinestore.services;

public interface AuthenticationService {
	boolean validateUser(String username, String password, String role);
}
