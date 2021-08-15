import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register implements Runnable {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;

	private static Font Courier16 = new Font("Courier", Font.PLAIN, 16);

	@Override
	public void run() {
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
		// frame.setSize(200, 200);

		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// JButton button = new JButton("Button2");

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
		
		JLabel userTypeLabel = new JLabel("I am a ");
		userTypeLabel.setFont(Courier16);
		
		String[] selections = {"Student", "Teacher"};
		JComboBox userTypeInput = new JComboBox<String>(selections);
		
		JLabel registerLabel = new JLabel();

		JButton registerButton = new JButton("Register");
		registerButton.setFont(Courier16);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "REGISTER " + userInput.getText() + ":"+ passInput.getText() 
					+ ":" + userTypeInput.getSelectedItem().toString().toUpperCase() + ":" + nameInput.getText() + ":" + groupInput.getText();
				
				System.out.println(message);
				
				StartProgram.socket.sendMessage(message);
				String res = StartProgram.socket.receiveMessage();
				if (res.contentEquals("SUCCESS")) {
					registerLabel.setText("Registration successful.");
				} 
				if (res.contentEquals("BAD USERNAME")) {
					registerLabel.setText("Username is already taken.");
				}
				
				if (res.contentEquals("BAD GROUPCODE")) {
					registerLabel.setText("Groupcode is invalid.");
				}
			}
		});

		panel.setLayout(null);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// panel.add(button);
		panel.add(userLabel);
		userLabel.setBounds(10, 15, 80, 25);
		panel.add(userInput);
		userInput.setBounds(130, 15, 200, 25);

		panel.add(passLabel);
		passLabel.setBounds(10, 55, 80, 25);
		panel.add(passInput);
		passInput.setBounds(130, 55, 200, 25);

		panel.add(nameLabel);
		nameLabel.setBounds(10, 95, 80, 25);
		panel.add(nameInput);
		nameInput.setBounds(130, 95, 200, 25);

		panel.add(groupLabel);
		groupLabel.setBounds(10, 135, 150, 25);
		panel.add(groupInput);
		groupInput.setBounds(130, 135, 200, 25);
		
		panel.add(userTypeLabel);
		userTypeLabel.setBounds(10, 175, 150, 25);
		panel.add(userTypeInput);
		userTypeInput.setBounds(130, 175, 200, 25);

		panel.add(registerButton);
		registerButton.setBounds(125, 215, 150, 30);
		
		panel.add(registerLabel);
		registerLabel.setBounds(125, 265, 150, 30);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Register");
		frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	}
}
