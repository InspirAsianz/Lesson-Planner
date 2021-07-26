import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JPanelTest {
	
	public JPanelTest() {
		JFrame frame = new JFrame();
		frame.setSize(200, 200);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Hey everyone");
		
		JButton button = new JButton("Button");
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	label.setText("hello");
            	frame.dispose();
            	panel2();
            }
          });

		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(button);
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Frame");
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	private void panel2() {
		JFrame frame = new JFrame();
		frame.setSize(200, 200);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Yo wassup");
		
		JButton button = new JButton("Button2");
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
          });

		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(button);
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Frame");
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		JPanelTest test = new JPanelTest();
	}
}
