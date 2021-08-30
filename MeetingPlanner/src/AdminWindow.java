import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AdminWindow implements Runnable {
	
	private static Font Courier16 = new Font("Courier", Font.PLAIN, 16);

	@Override
	public void run() {
		int w = 800;
		int h = 600;
		
		JFrame frame = new JFrame();
		frame.setLayout(null);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(new GridBagLayout());
		frame.add(panel);
		panel.setBounds(0, 0, w, h);
		
		
		JLabel consoleOutput = new JLabel("<html>Admin Console></html>");
		consoleOutput.setBackground(Color.BLACK);
		consoleOutput.setOpaque(true);
		consoleOutput.setForeground(Color.WHITE);
		consoleOutput.setVerticalAlignment(JLabel.TOP);
		consoleOutput.setFont(Courier16);
		consoleOutput.setBorder(BorderFactory.createEmptyBorder(7,2,7,2));
		JScrollPane scroll = new JScrollPane(consoleOutput);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 0;
		gbc.ipady = 590;
		gbc.ipadx = 800;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(scroll, gbc);


		JTextField consoleInput = new HintTextField("Enter Command...");
		consoleInput.setFont(Courier16);
		consoleInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = consoleInput.getText().substring(0, Math.min(consoleInput.getText().length(), 64));
				String commandResult = "";
				if (StartProgram.socket != null) {
					StartProgram.socket.sendMessage("CONSOLE " + command + " " + StartProgram.username + " " + StartProgram.groupcode);
					commandResult = StartProgram.socket.receiveMessage();
				}
				else {
					commandResult = "Disconnected from Server";
				}
				String outputText = consoleOutput.getText().substring(0, consoleOutput.getText().length() - 7) + 
						" " + command + "<br>" + commandResult + "<br><br>Admin Console></html>";
				consoleOutput.setText(outputText);
				consoleInput.setText("");
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 765;
		gbc.ipady = 50;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(consoleInput, gbc);
		
		JButton help = new JButton("?");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 35;
		gbc.ipady = 50;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(help, gbc);

		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.addMouseListener(tsc);
//		frame.addMouseMotionListener(tsc);
		frame.setTitle("Lesson Planner Admin");
		frame.pack();
		frame.setSize(w+16, h+39);
		frame.setVisible(true);
		frame.setResizable(true);

	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new AdminWindow());
	}
}

class HintTextField extends JTextField implements FocusListener {

	  private final String hint;
	  private boolean showingHint;

	  public HintTextField(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.showingHint = true;
	    super.addFocusListener(this);
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText("");
	      showingHint = false;
	    }
	  }
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText(hint);
	      showingHint = true;
	    }
	  }

	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }
	}
