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

import com.yauhenikuntsevich.training.onlinestore.services.caching.AuthenticationServiceAdministratorCaching;

@Service
public class ExternalizableCacheAdministratorAuthenticationService {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalizableCacheAdministratorAuthenticationService.class);
	private static final String FILENAME = "src//main//resources//cache//cache-administrator-authentication-service.tmp";

	public static AuthenticationServiceAdministratorCaching createInstanceAuthenticationServiceAdministratorCaching() {
		try {
			return readCacheFromFile();
		} catch (FileNotFoundException e) {
			return new AuthenticationServiceAdministratorCaching();
		}
	}

	public static void writeCacheInFile(
			AuthenticationServiceAdministratorCaching authenticationServiceAdministratorCaching) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(authenticationServiceAdministratorCaching);
			out.flush();
			out.close();

			LOGGER.debug("Write in file AuthenticationServiceAdministratorCaching is done!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static AuthenticationServiceAdministratorCaching readCacheFromFile() throws FileNotFoundException {
		AuthenticationServiceAdministratorCaching authenticationServiceAdministratorCaching = null;

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(fis);
			authenticationServiceAdministratorCaching = (AuthenticationServiceAdministratorCaching) in.readObject();
			fis.close();
			in.close();
			deleteFile();
			LOGGER.debug("Read from file AuthenticationServiceAdministratorCaching is done, temporary file " + FILENAME
					+ " was deleted!");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return authenticationServiceAdministratorCaching;
	}

	public static void deleteFile() {
		File file = new File(FILENAME);
		file.delete();
	}
}
