package com.yauhenikuntsevich.training.onlinestore.datamodel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.yauhenikuntsevich.training.onlinestore.datamodel.AbstractModel.AbstractModel;

public class Category extends AbstractModel implements Externalizable{
	private String itemEn;
	private String itemRu;

	public Category() {
	}

	
	public String getItemEn() {
		return itemEn;
	}

	public void setItemEn(String itemEn) {
		this.itemEn = itemEn;
	}

	public String getItemRu() {
		return itemRu;
	}

	public void setItemRu(String itemRu) {
		this.itemRu = itemRu;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readLong();
		itemEn = (String) in.readObject();
		itemRu = (String) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(id);
		out.writeObject(itemEn);
		out.writeObject(itemRu);
	}
}
