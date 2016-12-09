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

import com.yauhenikuntsevich.training.onlinestore.services.caching.ClientCaching;

@Service
public class ExternalizableCacheClient {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheClient.class);
	private static final String FILENAME = "D:\\cache\\cache-client.tmp";

	public static ClientCaching createInstanceClientCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new ClientCaching();
		}
	}

	public static void writeCacheInFile(ClientCaching clientCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(clientCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file ClientCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static ClientCaching readCacheFromFile() throws FileNotFoundException {
		ClientCaching clientCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			clientCaching = (ClientCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file ClientCaching is done, temporary file " + FILENAME + " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return clientCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
