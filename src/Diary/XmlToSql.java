/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Diary;

import java.io.File;
import java.sql.Connection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Diary.database.DBUtil;

public class XmlToSql {
	public static void importData(String avg[]) {
		importData();
	}

	public static void importData() {
		try {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
					"xml files (*.xml)", "xml");
			fc.setDialogTitle("Select file to open");
			fc.setFileFilter(xmlfilter);
			File file = new File("data.xml"); // create xml data file
			int returnVal = fc.showOpenDialog(MainFrame.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("data"); // the tag of
																// each
																// person/account
			Connection conn = DBUtil.getConnection();
			// connection to ConnectDB
			// class
			conn.createStatement().execute("DROP TABLE IF EXISTS diary");
			conn.createStatement()
					.execute(
							"CREATE TABLE diary ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username text, event text, date char(25), title char(25), mood int);");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String sql = "INSERT INTO `diary` (username, title,date,event) VALUES ('"
							+ getValue("username", element)
							+ "' , '"
							+ getValue("title", element)
							+ "' , '"
							+ getValue("date", element)
							+ "' , '"
							+ getValue("event", element) + "')";
					conn.createStatement().execute(sql); // Insert

					System.out.println(sql);
				}
			}
			conn.close();
			JOptionPane.showMessageDialog(null,
					"XML Data sussucfuly uploaded to MySQL database.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
