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
	private final static String FILENAME = "tempFile.tmp";

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

			MyLogger.LOGGER.debug("Write in file cache of administrator is done!");
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
			
			new File(FILENAME).delete();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return administratorCaching;
	}
}
