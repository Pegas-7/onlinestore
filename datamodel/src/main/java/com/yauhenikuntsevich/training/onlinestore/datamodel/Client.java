package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.PersonAbstractModel;

public class Client extends PersonAbstractModel implements Externalizable {
	private Integer age;
	private Boolean blacklisted;

	public Client() {
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(Boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		firstName = (String) in.readObject();
		lastName = (String) in.readObject();
		password = (String) in.readObject();
		role = (String) in.readObject();
		age = (Integer) in.readObject();
		blacklisted = (Boolean) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(firstName);
		out.writeObject(lastName);
		out.writeObject(password);
		out.writeObject(role);
		out.writeInt(age);
		out.writeBoolean(blacklisted);
	}
}
