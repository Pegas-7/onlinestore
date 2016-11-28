package com.yauhenikuntsevich.training.onlinestore.daoapi;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public interface AuthenticationDao {
	PersonAbstractModel get(String username);
}
