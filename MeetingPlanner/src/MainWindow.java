import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements Runnable {

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
		JTextField addGroupText = new JTextField();
		JButton addGroupButton = new JButton();
		addGroupPanel.add(addGroupLabel);
		addGroupLabel.add(addGroupText);
		addGroupPanel.add(addGroupButton);
		addGroupLabel.setText("Add a Group");
		addGroupLabel.setBounds(20, 0, 180, 80);
		addGroupText.setBounds(0, 50, 120,20);
		addGroupButton.setBounds(20, 70, 100, 30);
		addGroupButton.setText("Add");
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartProgram.socket.sendMessage("JOINGROUP" + StartProgram.username+ "" + addGroupText.getText());
			}
			});
		addGroupLabel.setVisible(true);
		addGroupText.setVisible(true);
		panel.add(addGroupPanel);
		addGroupPanel.setBounds(0, 80, 200, 120);
		addGroupPanel.setBorder(new LineBorder(Color.YELLOW));
		
		
		
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
