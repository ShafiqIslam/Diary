package Diary;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Diary.database.Database;

public class AddDairyForm implements ActionListener {

	String Date = new SimpleDateFormat("d-MMM-yyyy  hh:mm aa").format(Calendar
			.getInstance().getTime()); // get time
	HTMLDocumentEditor editorPane = new HTMLDocumentEditor(); // initialize JTextPane
	
	JTextField titleTextField = new JTextField();
	//JTextField dateTextField = new JTextField(Date);
	JTextField dateTextField;
	JCheckBox chkFav;
	JFrame parent;

	public AddDairyForm(JFrame p, String date) {
		parent = p;
		if(date.length() != 0) {
			Date = date;
		}
		dateTextField = new JTextField(Date);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (((editorPane.getText().equals("")) || (titleTextField.getText()
				.equals(""))) == false) // check if any field is left empty
		{

			int favourite;
			boolean a = chkFav.isSelected();
			if(a == true) {
				favourite = 1;
			} else {
				favourite = 0;
			}

			Database.insertDiary(MainFrame.username, editorPane.getText(),
					dateTextField.getText(), titleTextField.getText(), favourite);
			
	
			JOptionPane.showMessageDialog(null, "Saved"); // message box
															// telling
															// 'saved'
			editorPane.setText(""); // reset text field after data saved
			titleTextField.setText("");
		} else {
			JOptionPane.showConfirmDialog(null, "You Didn't Type Anything !");
		}
	}

	public JPanel createContentPane() // create content ( forms)
	{
		JPanel totalGUI = new JPanel(); // panel to fit all my buttons,forms
										// inside
		totalGUI.setLayout(null);

		// Creation of a Panel to contain the JLabels
		JPanel textPanel = new JPanel();
		textPanel.setLayout(null);
		textPanel.setLocation(0, 0);
		textPanel.setSize(400, 400);
		totalGUI.add(textPanel);

		JLabel dateLabel = new JLabel("Date : ");
		dateLabel.setLocation(10, 10);
		dateLabel.setSize(50, 30);
		textPanel.add(dateLabel);

		dateTextField.setLocation(60, 10);
		dateTextField.setSize(150, 30);
		textPanel.add(dateTextField);

		JLabel titleLabel = new JLabel("Title : ");
		titleLabel.setLocation(10, 50);
		titleLabel.setSize(50, 30);
		textPanel.add(titleLabel);

		titleTextField.setLocation(60, 50);
		titleTextField.setSize(150, 30);
		textPanel.add(titleTextField);

		JButton saveButton = new JButton("Save");
		saveButton.setLocation(330, 10);
		saveButton.setSize(70, 70);
		saveButton.addActionListener(this);
		textPanel.add(saveButton);
		
		chkFav = new JCheckBox("Favourite");
		chkFav.setLocation(320, 80);
		chkFav.setSize(150, 50);
		textPanel.add(chkFav);

		JLabel todayStoryLabel = new JLabel("Today's Story:");
		todayStoryLabel.setLocation(10, 90);
		todayStoryLabel.setSize(150, 30);
		textPanel.add(todayStoryLabel);
		
		JScrollPane scPane = new JScrollPane(editorPane);
		scPane.setLocation(10, 120);
		scPane.setSize(400, 200);
		textPanel.add(scPane);

		return totalGUI;
	}

	void makeGUI() {

		ReadDairyEventForm demo = new ReadDairyEventForm(parent);
		JDialog frame = new JDialog(parent);
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		frame.setResizable(false);
		frame.setLocation(40, 20);
		frame.setSize(430, 370);
		frame.setContentPane(this.createContentPane());
		frame.setTitle("Write into Diary");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
