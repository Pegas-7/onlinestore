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

import com.yauhenikuntsevich.training.onlinestore.services.caching.ProductCaching;

@Service
public class ExternalizableCacheProduct {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheProduct.class);
	private static final String FILENAME = "src//main//resources//cache//cache-product.tmp";

	public static ProductCaching createInstanceProductCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new ProductCaching();
		}
	}

	public static void writeCacheInFile(ProductCaching productCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(productCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file ProductCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static ProductCaching readCacheFromFile() throws FileNotFoundException {
		ProductCaching productCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			productCaching = (ProductCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file ProductCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return productCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
