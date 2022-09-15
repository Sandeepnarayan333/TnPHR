package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private static DBConnectionManager instance;
	private Connection conn;

	private DBConnectionManager(String url, String username, String password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // It is used to load the jar
			this.conn = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return this.conn;
	}

	// To reUse the DB Connection
	public static DBConnectionManager getInstance(String url, String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new DBConnectionManager(url, username, password);
		} else if (instance.getConnection().isClosed()) {
			instance = new DBConnectionManager(url, username, password);

		}
		return instance;
	}

}
