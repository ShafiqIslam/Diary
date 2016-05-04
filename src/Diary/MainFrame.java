package Diary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class MainFrame implements ActionListener {
	public static JFrame frame;
	public static String username = "shafiq";
	
	JDatePickerImpl datePicker;

	public JPanel createContentPane() {

		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);

		JButton readButton = new JButton("Write New Story");
		readButton.setLocation(15, 50);
		readButton.setSize(200, 40);
		readButton.setName("write");
		readButton.addActionListener(this);
		totalGUI.add(readButton);

		JButton writeButton = new JButton("Read Dairy");
		writeButton.setLocation(15, 100);
		writeButton.setSize(200, 40);
		writeButton.setName("read");
		writeButton.addActionListener(this);
		totalGUI.add(writeButton);

		JButton exportXmlButton = new JButton("Export Data To XML File");
		exportXmlButton.setLocation(15, 150);
		exportXmlButton.setSize(200, 40);
		exportXmlButton.setName("outxml");
		exportXmlButton.addActionListener(this);
		totalGUI.add(exportXmlButton);

		JButton importXmlButton = new JButton("Import Data From XML File");
		importXmlButton.setLocation(15, 200);
		importXmlButton.setSize(200, 40);
		importXmlButton.setName("inxml");
		importXmlButton.addActionListener(this);
		totalGUI.add(importXmlButton);

		return totalGUI;
	}

	public void makeGUI() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(600, 300);

		JPanel panel = this.createContentPane();
		panel.setBounds(0, 0, 300, 300);
		frame.add(panel);
		// Calendar
		UtilDateModel model = new UtilDateModel();
		// model.setDate(20, 04, 2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePanel.setBounds(300, 0, 290, 270);
		
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		frame.add(datePanel);

		frame.setLocationRelativeTo(null);
		// frame.setContentPane(this.createContentPane());
		frame.setTitle("Personal Diary");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void InitApp() {

		MainFrame frame = new MainFrame();

		frame.makeGUI();

	}

	public void actionPerformed(ActionEvent e) {
		String date = datePicker.getJFormattedTextField().getText();
		//System.out.println(date);

		String buttonName = ((JComponent) e.getSource()).getName();
		switch (buttonName) {
		case "write":
			AddDairyForm addform = new AddDairyForm(frame, date);
			addform.makeGUI();
			break;
		case "read":
			ReadDairyEventForm readform = new ReadDairyEventForm(frame);
			readform.run();
			break;
		case "inxml":
			XmlToSql sqlform = new XmlToSql();
			sqlform.importData();
			break;
		case "outxml":
			try {
				SqlToXml.dumpData();
			} catch (SQLException ex) {
				Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (IOException ex) {
				Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE,
						null, ex);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}

}
