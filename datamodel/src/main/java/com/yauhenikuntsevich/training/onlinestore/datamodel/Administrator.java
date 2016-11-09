package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Administrator extends AbstractModel implements Externalizable {
	private String firstName;
	private String lastName;

	public Administrator() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		firstName = (String) in.readObject();
		lastName = (String) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(firstName);
		out.writeObject(lastName);
	}
}
