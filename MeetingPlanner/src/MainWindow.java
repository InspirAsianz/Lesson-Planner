import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements Runnable {

	private static Font textFont = new Font("Courier", Font.PLAIN, 16);
	private static Font Courier12 = new Font("Courier", Font.PLAIN, 12);
	
	@Override
	public void run() {
		int w = 1200;
		int h = 800;
		
		JFrame frame = new JFrame();
		frame.setLayout(null);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		frame.add(panel);
		panel.setBounds(0, 0, w, h);
		panel.setBorder(new LineBorder(Color.BLUE));
		
		TimeSelectionCanvas tsc = new TimeSelectionCanvas(1000, 800, 80, 80, 90, 94);
		panel.add(tsc);
		tsc.setBounds(200, 0, 1000, 800);
		tsc.setBorder(new LineBorder(Color.RED));
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(null);
		settingsPanel.setLayout(null);
		panel.add(settingsPanel);
		settingsPanel.setBounds(0, 0, 200, 100);
		settingsPanel.setBorder(new LineBorder(Color.GREEN));
		
		JPanel addGroupPanel = new JPanel();
		addGroupPanel.setBorder(null);
		addGroupPanel.setLayout(null);
		
		JLabel addGroupLabel = new JLabel();
		addGroupLabel.setText("Add a Group");
		addGroupLabel.setFont(textFont);

		JTextField addGroupText = new JTextField();
		addGroupText.setFont(textFont);
		
		JButton addGroupButton = new JButton();
		addGroupButton.setText("Add");
		addGroupButton.setFont(textFont);
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartProgram.socket.sendMessage("JOINGROUP" + StartProgram.username+ "" + addGroupText.getText());
			}
		});

		addGroupPanel.add(addGroupLabel);
		addGroupLabel.add(addGroupText);
		addGroupPanel.add(addGroupButton);
		addGroupLabel.setBounds(20, 0, 180, 80);
		addGroupText.setBounds(0, 50, 120, 30);
		addGroupButton.setBounds(20, 80, 100, 30);
		
		panel.add(addGroupPanel);
		addGroupPanel.setBounds(0, 80, 200, 120);
		addGroupPanel.setBorder(new LineBorder(Color.YELLOW));
		
		JPanel classesPanel = new JPanel();
		classesPanel.setBorder(null);
		classesPanel.setLayout(null);
		JLabel addClassLabel = new JLabel("Add a Class");
		addClassLabel.setFont(textFont);
		
		String[] addSelections = {"No classes found"};
		if (StartProgram.socket != null) {
			StartProgram.socket.sendMessage("GETADDCLASSES " + StartProgram.username + " " + StartProgram.groupcode);
			addSelections = StartProgram.socket.receiveMessage().split(":");
		}
		JComboBox<String> addClassSelection = new JComboBox<String>(addSelections);
		addClassSelection.setFont(Courier12);
		
		JButton addClassButton = new JButton();
		addClassButton.setText("Add");
		addClassButton.setFont(Courier12);
		addClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				StartProgram.socket.sendMessage("CONSOLE ADD " + StartProgram.username + " TO " + 
//						addClassSelection.getSelectedItem());
			}
		});
		
		String[] currentClasses = {"No classes found"};
		if (StartProgram.socket != null) {
			StartProgram.socket.sendMessage("GETCURCLASSES " + StartProgram.username + " " + StartProgram.groupcode);
			currentClasses = StartProgram.socket.receiveMessage().split(":");
		}

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
		
		String[] removeSelections = currentClasses;
		JComboBox<String> removeClassSelection = new JComboBox<String>(removeSelections);
		removeClassSelection.setFont(Courier12);
		
		JButton removeClassButton = new JButton();
		removeClassButton.setText("Remove");
		removeClassButton.setFont(Courier12);


		classesPanel.add(addClassLabel);
		addClassLabel.setBounds(20, 10, 180, 30);
		classesPanel.add(addClassSelection);
		addClassSelection.setBounds(20, 35, 160, 20);
		classesPanel.add(addClassButton);
		addClassButton.setBounds(20, 56, 80, 20);
		classesPanel.add(currentClassesLabel);
		currentClassesLabel.setBounds(20, 80, 160, 30);
		classesPanel.add(curClassesLabel);
		curClassesLabel.setBounds(20, 100, 160, 300);
		classesPanel.add(removeClassLabel);
		removeClassLabel.setBounds(20, 410, 160, 30);
		classesPanel.add(removeClassSelection);
		removeClassSelection.setBounds(20, 435, 160, 20);
		classesPanel.add(removeClassButton);
		removeClassButton.setBounds(20, 456, 80, 20);
		
		panel.add(classesPanel);
		classesPanel.setBounds(0, 200, 200, 600);
		classesPanel.setBorder(new LineBorder(Color.PINK));
		
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(tsc);
		frame.addMouseMotionListener(tsc);
		frame.setTitle("Lesson Planner");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
		frame.setResizable(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MainWindow());
	}
}
