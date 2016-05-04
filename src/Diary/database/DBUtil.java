package Diary.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String dir = System.getProperty("user.home")
			+ "\\Documents\\diary";
	private static final String CONN_STRING = "jdbc:sqlite:" + dir
			+ "\\diary.db";
	static {
		File file = new File(dir);
		if (!file.exists())
			file.mkdirs();
	}

	public static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(CONN_STRING);
	}
}
