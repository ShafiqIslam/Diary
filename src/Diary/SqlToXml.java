/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Diary;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Diary.database.DBUtil;

public class SqlToXml {
	String[] titleString;

	public static void dumpData() throws Exception {
		// database

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
				"xml files (*.xml)", "xml");
		fc.setDialogTitle("Where to Save");
		fc.setFileFilter(xmlfilter);

		File file = new File("data.xml"); // create xml data file
		int returnVal = fc.showSaveDialog(MainFrame.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		file.createNewFile();
		FileWriter writer = new FileWriter(file); // write in xml file
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<diary>\n");

		String query = "SELECT * FROM diary"; // sql script

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);) {
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) // go through each row that your query
										// returns
			{
				writer.write("\t <data> \n");
				// writer.write("\t\t<id>" + resultSet.getString("id") +
				// "</id>\n");
				writer.write("\t\t<username>" + resultSet.getString("username")
						+ "</username>\n"); // get the element in column "title"
				writer.write("\t\t<title>" + resultSet.getString("title")
						+ "</title>\n"); // get the element in column "title"
				writer.write("\t\t<date>" + resultSet.getString("date")
						+ "</date>\n");
				writer.write("\t\t<event>" + resultSet.getString("event")
						+ "</event>\n");
				writer.write("\t </data> \n");
			}
			writer.write("</diary>\n");

			writer.flush();
			writer.close();
			resultSet.close();
			JOptionPane.showMessageDialog(null,
					"Data Sussucfully exported to data.xml file");

		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	public static void dump(String avg[]) throws Exception {
		dumpData();
	}

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}
