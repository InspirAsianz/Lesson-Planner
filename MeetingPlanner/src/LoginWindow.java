import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow implements Runnable {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	private static Font Courier16 = new Font("Courier", Font.PLAIN, 16);

	private static ActionListener button = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// do stuff
		}
	};

	@Override
	public void run() {
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		JLabel userLabel = new JLabel("Username");
		userLabel.setFont(Courier16);

		JTextField userInput = new JTextField(24);

		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(Courier16);

		JPasswordField passInput = new JPasswordField(24);

		JLabel successLabel = new JLabel();

		JButton loginButton = new JButton("Log in");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartProgram.socket.sendMessage("LOGINCHECK " + userInput.getText() + " " + passInput.getText());
				String res = StartProgram.socket.receiveMessage();
				if (res.contentEquals("SUCCESS")) {
					successLabel.setText("Login Successful");
				} else {
					successLabel.setText("Wrong Credentials");
				}
			}
		});
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
            	SwingUtilities.invokeLater(new Register());
            }
          });


//		panel.setLayout(new GridLayout(3, 3, 10, 10));
//		panel.add(userLabel);
//		panel.add(userInput);
//		panel.add(passLabel);
//		panel.add(passInput);
//		panel.add(successLabel);
//		panel.add(loginButton);

		panel.setLayout(null);

		panel.add(userLabel);
		userLabel.setBounds(10, 25, 80, 25);

		panel.add(userInput);
		userInput.setBounds(100, 25, 200, 25);

		panel.add(passLabel);
		passLabel.setBounds(10, 60, 80, 25);

		panel.add(passInput);
		passInput.setBounds(100, 60, 200, 25);

		panel.add(loginButton);
		loginButton.setBounds(40, 100, 160, 25);
		
		panel.add(registerButton);
		registerButton.setBounds(200, 100, 160, 25);

		panel.add(successLabel);
		successLabel.setBounds(120, 125, 160, 25);

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Log in");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(WIDTH, HEIGHT);

	}

}
