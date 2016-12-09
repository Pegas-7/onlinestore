package com.yauhenikuntsevich.training.onlinestore.services.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDB {
	private static final String URL = "jdbc:postgresql://localhost:5432/onlinestore";
	private static final String USER_NAME = "postgres";
	private static final String PASSWORD = "postgres1";

	public static Connection connectionBD() {
		try {
			return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			System.out.println("Сonnection failed");
			e.printStackTrace();
		}
		return null;
	}
}