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

import com.yauhenikuntsevich.training.onlinestore.services.caching.AuthenticationServiceClientCaching;

@Service
public class ExternalizableCacheClientAuthenticationService {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExternalizableCacheClientAuthenticationService.class);
	private static final String FILENAME = "D:\\cache\\cache-client-authentication-service.tmp";

	public static AuthenticationServiceClientCaching createInstanceAuthenticationServiceClientCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new AuthenticationServiceClientCaching();
		}
	}

	public static void writeCacheInFile(AuthenticationServiceClientCaching authenticationServiceClientCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(authenticationServiceClientCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file AuthenticationServiceClientCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static AuthenticationServiceClientCaching readCacheFromFile() throws FileNotFoundException {
		AuthenticationServiceClientCaching authenticationServiceClientCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			authenticationServiceClientCaching = (AuthenticationServiceClientCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file AuthenticationServiceClientCaching is done, temporary file " + FILENAME
					+ " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return authenticationServiceClientCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
