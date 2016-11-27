package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public class Administrator extends PersonAbstractModel implements Externalizable {

	public Administrator() {
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		firstName = (String) in.readObject();
		lastName = (String) in.readObject();
		password = (String) in.readObject();
		role = (String) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(firstName);
		out.writeObject(lastName);
		out.writeObject(password);
		out.writeObject(role);
	}
}
