import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow implements Runnable {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	private static Font Courier16 = new Font("Courier", Font.PLAIN, 16);

	private static ActionListener loginButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// do stuff
		}
	};

	@Override
	public void run() {
		SocketConnection socket = new SocketConnection();

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
				socket.sendMessage("LOGINCHECK " + userInput.getText() + " " + passInput.getText());
				String res = socket.receiveMessage();
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
