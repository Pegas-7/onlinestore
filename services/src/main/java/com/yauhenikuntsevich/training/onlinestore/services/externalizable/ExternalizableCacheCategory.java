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

import com.yauhenikuntsevich.training.onlinestore.services.caching.CategoryCaching;

@Service
public class ExternalizableCacheCategory {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheCategory.class);
	private static final String FILENAME = "src//main//resources//cache//cache-category.tmp";

	public static CategoryCaching createInstanceCategoryCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new CategoryCaching();
		}
	}

	public static void writeCacheInFile(CategoryCaching сategoryCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(сategoryCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file CategoryCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static CategoryCaching readCacheFromFile() throws FileNotFoundException {
		CategoryCaching сategoryCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			сategoryCaching = (CategoryCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file CategoryCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return сategoryCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
