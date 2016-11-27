package com.yauhenikuntsevich.training.onlinestore.services;

public interface AuthenticationService {

	boolean validateUserPassword(String username, String password);
}
