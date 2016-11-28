package com.yauhenikuntsevich.training.onlinestore.services.authentification;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AuthenticationDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;
import com.yauhenikuntsevich.training.onlinestore.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	@Inject
	private AuthenticationDao authenticationDao;

	@Override
	public boolean validateUser(String username, String password, String role) {
		PersonAbstractModel personAbstractModel = authenticationDao.get(username);
		return username.equals(personAbstractModel.getFirstName()) && password.equals(personAbstractModel.getPassword())
				&& role.equals(personAbstractModel.getRole());
	}

}
