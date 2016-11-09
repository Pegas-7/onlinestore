package com.yauhenikuntsevich.training.onlinestore.services.externalizable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.stereotype.Service;

import com.yauhenikuntsevich.training.onlinestore.services.caching.AdministratorCaching;
import com.yauhenikuntsevich.training.onlinestore.services.util.MyLogger;

@Service
public class ExternalizableCacheAdministrator {
	private final static String FILENAME = "tempFileCacheAdministrator.tmp";

	public static AdministratorCaching createInstanceAdministratorCaching() {
		try {
			return readFromFileCacheAdministrator();
		} catch (FileNotFoundException e) {
			return new AdministratorCaching();
		}
	}

	public static void writeInFileCacheAdministrator(AdministratorCaching administratorCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(administratorCaching);
			out.flush();
			out.close();

			MyLogger.LOGGER.debug("Write in file AdministratorCaching is done!");
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
			MyLogger.LOGGER
					.debug("Read from file AdministratorCaching is done, temporary file " + FILENAME + " was deleted!");
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
