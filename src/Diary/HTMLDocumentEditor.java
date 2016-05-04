package Diary;

/*	HTMLDocumentEditor.java
 *	@author: Charles Bell
 *	@version: May 27, 2002	
 */

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

public class HTMLDocumentEditor extends JScrollPane {

	private HTMLDocument document;
	private JTextPane textPane = new JTextPane();
	private boolean debug = false;
	private File currentFile;

	/** Listener for the edits on the current document. */

	/** UndoManager that we add edits to. */
	protected UndoManager undo = new UndoManager();

	private Action boldAction = new StyledEditorKit.BoldAction();
	private Action underlineAction = new StyledEditorKit.UnderlineAction();
	private Action italicAction = new StyledEditorKit.ItalicAction();

	public HTMLDocumentEditor() {
		HTMLEditorKit editorKit = new HTMLEditorKit();
		document = (HTMLDocument) editorKit.createDefaultDocument();
		init();
	}
	public void init() {
		JMenu colorMenu = new JMenu("Color");
		JMenu fontMenu = new JMenu("Font");
		JMenu styleMenu = new JMenu("Style");

		JMenuItem redTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Red", Color.red));
		JMenuItem orangeTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Orange", Color.orange));
		JMenuItem yellowTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Yellow", Color.yellow));
		JMenuItem greenTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Green", Color.green));
		JMenuItem blueTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Blue", Color.blue));
		JMenuItem cyanTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Cyan", Color.cyan));
		JMenuItem magentaTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Magenta", Color.magenta));
		JMenuItem blackTextItem = new JMenuItem(
				new StyledEditorKit.ForegroundAction("Black", Color.black));

		colorMenu.add(redTextItem);
		colorMenu.add(orangeTextItem);
		colorMenu.add(yellowTextItem);
		colorMenu.add(greenTextItem);
		colorMenu.add(blueTextItem);
		colorMenu.add(cyanTextItem);
		colorMenu.add(magentaTextItem);
		colorMenu.add(blackTextItem);

		JMenu fontTypeMenu = new JMenu("Font Type");
		fontMenu.add(fontTypeMenu);

		String[] fontTypes = { "SansSerif", "Serif", "Monospaced", "Dialog",
				"DialogInput" };
		for (int i = 0; i < fontTypes.length; i++) {
			if (debug)
				System.out.println(fontTypes[i]);
			JMenuItem nextTypeItem = new JMenuItem(fontTypes[i]);
			nextTypeItem.setAction(new StyledEditorKit.FontFamilyAction(
					fontTypes[i], fontTypes[i]));
			fontTypeMenu.add(nextTypeItem);
		}

		JMenu fontSizeMenu = new JMenu("Font Size");
		fontMenu.add(fontSizeMenu);

		int[] fontSizes = { 6, 8, 10, 12, 14, 16, 20, 24, 32, 36, 48, 72 };
		for (int i = 0; i < fontSizes.length; i++) {
			if (debug)
				System.out.println(fontSizes[i]);
			JMenuItem nextSizeItem = new JMenuItem(String.valueOf(fontSizes[i]));
			nextSizeItem.setAction(new StyledEditorKit.FontSizeAction(String
					.valueOf(fontSizes[i]), fontSizes[i]));
			fontSizeMenu.add(nextSizeItem);
		}

		JMenuItem boldMenuItem = new JMenuItem(boldAction);
		JMenuItem underlineMenuItem = new JMenuItem(underlineAction);
		JMenuItem italicMenuItem = new JMenuItem(italicAction);

		boldMenuItem.setText("Bold");
		underlineMenuItem.setText("Underline");
		italicMenuItem.setText("Italic");

		styleMenu.add(boldMenuItem);
		styleMenu.add(underlineMenuItem);
		styleMenu.add(italicMenuItem);

		JButton boldButton = new JButton(boldAction);
		JButton underlineButton = new JButton(underlineAction);
		JButton italicButton = new JButton(italicAction);

		boldButton.setText("Bold");
		underlineButton.setText("Underline");
		italicButton.setText("Italic");


		textPane = new JTextPane(document);
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu popupMenu;
					popupMenu = new JPopupMenu();
					popupMenu.add(colorMenu);
					popupMenu.add(fontMenu);
					popupMenu.add(styleMenu);
					popupMenu.show(textPane, e.getX(), e.getY());

				}
			}
		});
		textPane.setContentType("text/html");		
		setViewportView(textPane);
	}
	public String getText(){
		return textPane.getText();
	}
	public void setText(String text){
		textPane.setText(text);
	}

}