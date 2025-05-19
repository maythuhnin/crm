package com.eniac.projects.crm.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {

	public Connection getConnection() {

		Resources resources = new Resources();
		ResourceBundle resourceBundle = resources.getApplicationResources();
		Connection conn = null;

		try {

			String dbURL = resourceBundle.getString("db.connection");
			String user = resourceBundle.getString("db.user");
			String pass = resourceBundle.getString("db.password");
			conn = DriverManager.getConnection(dbURL, user, pass);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			
		}

		return conn;
	}
}
