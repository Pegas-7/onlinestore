package com.yauhenikuntsevich.training.onlinestore.services.externalizable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.services.caching.OrderItemCaching;

@Service
public class ExternalizableCacheOrderItem {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheOrderItem.class);
	private static final String FILENAME = "D:\\cache\\cache-orderitem.tmp";

	public static OrderItemCaching createInstanceOrderItemCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new OrderItemCaching();
		}
	}

	public static void writeCacheInFile(OrderItemCaching orderItemCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(orderItemCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file OrderItemCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static OrderItemCaching readCacheFromFile() throws FileNotFoundException {
		OrderItemCaching orderItemCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			orderItemCaching = (OrderItemCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file OrderItemCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return orderItemCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
