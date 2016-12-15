package com.yauhenikuntsevich.training.onlinestore.services.authentification;

import java.util.Map;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AuthenticationDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;
import com.yauhenikuntsevich.training.onlinestore.services.AuthenticationService;
import com.yauhenikuntsevich.training.onlinestore.services.caching.AuthenticationServiceAdministratorCaching;
import com.yauhenikuntsevich.training.onlinestore.services.externalizable.ExternalizableCacheAdministratorAuthenticationService;

@Service
public class AuthenticationServiceAdministratorImpl implements AuthenticationService {
	@Inject
	private AuthenticationDao<Administrator> authenticationDao;

	public static AuthenticationServiceAdministratorCaching authenticationServiceAdministratorCaching = ExternalizableCacheAdministratorAuthenticationService
			.createInstanceAuthenticationServiceAdministratorCaching();

	@Override
	public boolean validateUser(String username, String password, String role) {
		if (checkCache(username, password, role)) {
			return true;
		}

		PersonAbstractModel personAbstractModel = null;

		try {
			personAbstractModel = authenticationDao.get(username);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

		Boolean isAuthenticated = username.equals(personAbstractModel.getFirstName())
				&& password.equals(personAbstractModel.getPassword()) && role.equals(personAbstractModel.getRole());

		if (isAuthenticated) {
			authenticationServiceAdministratorCaching.putInCache(personAbstractModel.getFirstName(),
					personAbstractModel);
		}

		return isAuthenticated;
	}

	private boolean checkCache(String username, String password, String role) {
		Map<String, PersonAbstractModel> cache = authenticationServiceAdministratorCaching.getCache();

		if (cache.containsKey(username) && password.equals(cache.get(username).getPassword())
				&& role.equals(cache.get(username).getRole())) {
			return true;
		}
		return false;
	}

	@PreDestroy
	private void writeCacheToFile() {
		ExternalizableCacheAdministratorAuthenticationService
				.writeCacheInFile(authenticationServiceAdministratorCaching);
	}
}
