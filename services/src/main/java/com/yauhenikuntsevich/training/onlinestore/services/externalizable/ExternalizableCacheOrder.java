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

import com.yauhenikuntsevich.training.onlinestore.services.caching.OrderCaching;

@Service
public class ExternalizableCacheOrder {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheOrder.class);
	private static final String FILENAME = "src//main//resources//cache//cache-order.tmp";

	public static OrderCaching createInstanceOrderCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new OrderCaching();
		}
	}

	public static void writeCacheInFile(OrderCaching orderCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(orderCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file OrderCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static OrderCaching readCacheFromFile() throws FileNotFoundException {
		OrderCaching orderCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			orderCaching = (OrderCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file OrderCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return orderCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
