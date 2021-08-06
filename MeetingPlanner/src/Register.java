import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register implements Runnable{
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	private static Font Courier16 = new Font("Courier", Font.PLAIN, 16);
	
	@Override
	public void run() {
    	JFrame frame = new JFrame();
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
    	//frame.setSize(200, 200);
		
    	JPanel panel = new JPanel();
    	
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		//JButton button = new JButton("Button2");
		
		JLabel userLabel = new JLabel("Username");
		userLabel.setFont(Courier16);

		JTextField userInput = new JTextField(24);

		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(Courier16);

		JTextField passInput = new JTextField(24);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(Courier16);

		JTextField nameInput = new JTextField(24);
		
		JLabel groupLabel = new JLabel("Group Code");
		groupLabel.setFont(Courier16);

		JTextField groupInput = new JTextField(24);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		//panel.add(button);
		panel.add(userLabel);
		userLabel.setBounds(10, 15, 80, 25);
		panel.add(userInput);
		userInput.setBounds(100, 15, 200, 25);
		
		panel.add(passLabel);
		passLabel.setBounds(10, 55, 80, 25);
		panel.add(passInput);
		passInput.setBounds(100, 55, 200, 25);
		
		panel.add(nameLabel);
		nameLabel.setBounds(10, 95, 80, 25);
		panel.add(nameInput);
		nameInput.setBounds(100, 95, 200, 25);
		
		panel.add(groupLabel);
		groupLabel.setBounds(10, 135, 150, 25);
		panel.add(groupInput);
		groupInput.setBounds(130, 135, 200, 25);
		
		
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Register");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(WIDTH, HEIGHT);

	}
}
