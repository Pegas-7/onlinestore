package com.yauhenikuntsevich.training.onlinestore.daoxml;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AuthenticationDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Administrator;
import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

@Repository
public class AuthenticationAdministratorDaoXmlImpl implements AuthenticationDao<Administrator> {

	@Override
	public PersonAbstractModel get(String username) {
		throw new UnsupportedOperationException();
	}
}
