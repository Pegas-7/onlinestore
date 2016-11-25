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

import com.yauhenikuntsevich.training.onlinestore.services.caching.AdministratorCaching;

@Service
public class ExternalizableCacheAdministrator {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheAdministrator.class);
	private static final String FILENAME = "E://YandexDisk//Java//Projects//onlinestore//services//src//main//resources//cache//cache-administrator.tmp";

	public static AdministratorCaching createInstanceAdministratorCaching() {
		try {
			return readFromFileCacheAdministrator();
		} catch (FileNotFoundException e) {
			return new AdministratorCaching();
		}
	}

	public static void writeAdministratorCacheToFile(AdministratorCaching administratorCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(administratorCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file AdministratorCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static AdministratorCaching readFromFileCacheAdministrator() throws FileNotFoundException {
		AdministratorCaching administratorCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			administratorCaching = (AdministratorCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file AdministratorCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return administratorCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
