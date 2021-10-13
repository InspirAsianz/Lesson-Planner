import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GroupSelectionWindow implements Runnable {
	
	private static Font textFont = new Font("Courier", Font.PLAIN, 12);

	@Override
	public void run() {
		int w = 300;
		int h = 150;

		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		
		String[] selections = {"No Connection"};
		if (StartProgram.socket != null) {
			StartProgram.socket.sendMessage("GETGROUPS " + StartProgram.username);
			String groups = StartProgram.socket.receiveMessage();
			selections = groups.split("::");
		}
		JComboBox<String> groupSelectionBox = new JComboBox<String>(selections);
		groupSelectionBox.setFont(textFont);
		panel.add(groupSelectionBox);
		groupSelectionBox.setBounds(20, 15, 250, 30);

		JButton selectButton = new JButton("Select");
		selectButton.setFont(textFont);
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = (String) groupSelectionBox.getSelectedItem();
				if (!selection.contains("(")) return;
				StartProgram.groupcode = selection.split("\\(")[1].split("\\)")[0];
				String usertype = selection.split(" - ")[1];
				StartProgram.usertype = usertype;
				frame.dispose();
				if (usertype.equals("ADMIN")) {
					SwingUtilities.invokeLater(new AdminWindow());
				}
				else {
					SwingUtilities.invokeLater(new MainWindow());
				}
			}
		});
		panel.add(selectButton);
		selectButton.setBounds(100, 60, 100, 30);
		
		frame.add(panel, BorderLayout.CENTER);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Group Selection");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new GroupSelectionWindow());
	}
}
