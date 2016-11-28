package com.yauhenikuntsevich.training.onlinestore.daoapi;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public interface AuthenticationDao<T> {
	PersonAbstractModel get(String username);
}
