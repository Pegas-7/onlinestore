package com.yauhenikuntsevich.training.onlinestore.daoxml;

import org.springframework.stereotype.Repository;

import com.yauhenikuntsevich.training.onlinestore.daoapi.AuthenticationDao;
import com.yauhenikuntsevich.training.onlinestore.datamodel.Client;
import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

@Repository
public class AuthenticationClientDaoXmlImpl implements AuthenticationDao<Client> {

	@Override
	public PersonAbstractModel get(String username) {
		throw new UnsupportedOperationException();
	}
}
