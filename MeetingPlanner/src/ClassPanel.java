import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ClassPanel extends JPanel {
	
	private static Font textFont = new Font("Courier", Font.PLAIN, 16);
	private static Font Courier12 = new Font("Courier", Font.PLAIN, 12);
	
	private ArrayList<String> addSelections = new ArrayList<String>();
	private ArrayList<String> currentClasses = new ArrayList<String>();

	
	public ClassPanel() {
		setBorder(null);
		setLayout(null);
		
		JLabel addClassLabel = new JLabel("Add a Class");
		addClassLabel.setFont(textFont);
		
		if (StartProgram.socket != null) {
			StartProgram.socket.sendMessage("GETCLASSINFO " + StartProgram.username + " " + StartProgram.groupcode);
			String[] result = StartProgram.socket.receiveMessage().split(";");
			if (result.length > 1) addSelections = toStringArrayList(result[1].split(":"));
			currentClasses = toStringArrayList(result[0].split(":"));
		}
		
		JComboBox<String> addClassSelection = new JComboBox<String>(toStringArray(addSelections));
		addClassSelection.setFont(Courier12);
		
		JButton addClassButton = new JButton();
		addClassButton.setText("Add");
		addClassButton.setFont(Courier12);		

		JLabel currentClassesLabel = new JLabel("Current Classes:");
		currentClassesLabel.setFont(textFont);
		
		JLabel curClassesLabel = new JLabel();
		curClassesLabel.setFont(Courier12);
		StringBuilder classes = new StringBuilder("");
		for (String c : currentClasses) {
			classes.append(c);
			classes.append("<br>");
		}
		curClassesLabel.setText("<html>" + classes + "</html>");
		curClassesLabel.setVerticalAlignment(JLabel.TOP);
		
		JLabel removeClassLabel = new JLabel("Remove a Class");
		removeClassLabel.setFont(textFont);
		
		JComboBox<String> removeClassSelection = new JComboBox<String>(toStringArray(currentClasses));
		removeClassSelection.setFont(Courier12);
		
		JButton removeClassButton = new JButton();
		removeClassButton.setText("Remove");
		removeClassButton.setFont(Courier12);
		
		addClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = addClassSelection.getSelectedItem();
				if (selected == null) return;
				
				StartProgram.socket.sendMessage("CONSOLE ADD " + StartProgram.username + " TO " + 
						selected.toString() + " " + StartProgram.username + " " + StartProgram.groupcode);
				StartProgram.socket.receiveMessage();
				
				addSelections.remove(selected.toString());
				currentClasses.add(selected.toString());

				editComboBox(addClassSelection, addSelections);
				editJLabel(curClassesLabel, currentClasses);
				editComboBox(removeClassSelection, currentClasses);
			}
		});
		
		removeClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object selected = removeClassSelection.getSelectedItem();
				if (selected == null) return;
				
				StartProgram.socket.sendMessage("CONSOLE REMOVE " + StartProgram.username + " FROM " + 
						selected.toString() + " " + StartProgram.username + " " + StartProgram.groupcode);
				StartProgram.socket.receiveMessage();
				
				addSelections.add(selected.toString());
				currentClasses.remove(selected.toString());

				editComboBox(addClassSelection, addSelections);
				editJLabel(curClassesLabel, currentClasses);
				editComboBox(removeClassSelection, currentClasses);
			}
		});

		
		add(addClassLabel);
		addClassLabel.setBounds(20, 10, 180, 30);
		add(addClassSelection);
		addClassSelection.setBounds(20, 35, 160, 20);
		add(addClassButton);
		addClassButton.setBounds(20, 56, 80, 20);
		add(currentClassesLabel);
		currentClassesLabel.setBounds(20, 80, 160, 30);
		add(curClassesLabel);
		curClassesLabel.setBounds(20, 100, 160, 300);
		add(removeClassLabel);
		removeClassLabel.setBounds(20, 410, 160, 30);
		add(removeClassSelection);
		removeClassSelection.setBounds(20, 435, 160, 20);
		add(removeClassButton);
		removeClassButton.setBounds(20, 456, 80, 20);

	}
	
	private void editComboBox(JComboBox<String> box, ArrayList<String> options) {
		box.removeAllItems();
		for (String s : options) {
			box.addItem(s);
		}
	}
	
	private void editJLabel(JLabel label, ArrayList<String> options) {
		StringBuilder classes = new StringBuilder("");
		for (String c : options) {
			classes.append(c);
			classes.append("<br>");
		}
		label.setText("<html>" + classes + "</html>");
	}
	
	private String[] toStringArray(ArrayList<String> arrList) {
		String[] res = new String[arrList.size()];
		for (int i = 0; i < arrList.size(); i++) {
			res[i] = arrList.get(i);
		}
		return res;
	}
	
	private ArrayList<String> toStringArrayList(String[] arr) {
		ArrayList<String> res = new ArrayList<String>();
		for (String s : arr) {
			res.add(s);
		}
		return res;
	}

}
