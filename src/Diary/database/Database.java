package Diary.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Database {
	Connection con = null;

	public Database() {

		try {
			try {
				createUserTable();
				createDiaryTable();
			} catch (Exception e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isFoundTable(String tableName) throws Exception {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"
				+ tableName + "'; ";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			ResultSet resultSet = stmt.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}

	}

	public static boolean checkUser(String username, String pasword)
			throws Exception {
		String sql = "select username, password from users where `username`='"
				+ username + "' and " + "password ='" + pasword + "';";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			ResultSet set = stmt.executeQuery();
			return set.next();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(
					"Server Error! Please try again Leter. Or dublicate username."
							+ e.getMessage());
		}
	}

	public static void createUser(String username, String pasword)
			throws Exception {
		String sql = "insert into users(username, password) values('"
				+ username + "', '" + pasword + "'); ";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception(
					"Server Error! Please try again Leter. Or dublicate username."
							+ e.getMessage());
		}
	}

	public static void createUserTable() throws Exception {
		if (isFoundTable("users")) {
			throw new Exception("users" + " table already exists.");
		}
		String sql = "CREATE TABLE users ( username  text, password text, primary key(username));";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	public static void createDiaryTable() throws Exception {
		if (isFoundTable("diary")) {
			throw new Exception("diary" + " table already exists.");
		}
		String sql = "CREATE TABLE diary ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username text, event text, date char(25), title char(25), mood int, favourite int);";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	public static void insertDiary(String username, String event, String date,
			String title, int favourite) {
		String sql = "INSERT INTO `diary` (`username`, `event`, `favourite`, `date`, `title`) VALUES (\'"
				+ username
				+ "\' , \'"
				+ event
				+ "\' , \'"
				+ favourite
				+ "\' , \'"
				+ date
				+ "\' , \'"
				+ title + "\');"; // ConnectDB class
		//System.out.println(sql);
		
		if (((event.equals("")) || (date.equals(""))) == false) {
			try (Connection conn = DBUtil.getConnection();
					java.sql.PreparedStatement stmt = conn.prepareStatement(
							sql, Statement.RETURN_GENERATED_KEYS);) {
				conn.createStatement().executeUpdate(sql);
				conn.close(); // close mysql connection
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, err); // show error
			}
		} else {
			JOptionPane.showConfirmDialog(null, "You Did not Type Anything !");
		}
	}
}
