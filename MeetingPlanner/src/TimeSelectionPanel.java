import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;

public class TimeSelectionPanel extends JPanel {
	
	private static Font bigfont = new Font("Serif", Font.PLAIN, 24);
	private static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private JPanel available;
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	
	public TimeSelectionPanel() {
		
//		try { 
//		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
		
		setBorder(null);
		setLayout(null);
		
		JPanel inputs = new JPanel();
		
		JButton addTime = new JButton("Add Timeslot");
		addTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int day = (int)(Math.random() * 7);
            	int start = (int)(Math.random() * 24) + 1;
            	int end = (int)(Math.random() * 24) + 1;
            	addTime(day, start * 100, end * 100);
            }
		});
		inputs.add(addTime);
		
		add(inputs);
		inputs.setBounds(150, 0, 635, 200);
		inputs.setBorder(new LineBorder(Color.BLUE));

		
		available = new JPanel();
		available.setLayout(new GridBagLayout());
		JScrollPane scrollAvailable = new JScrollPane(available);
		scrollAvailable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JLabel currentTimes = new JLabel("Your current Availabilities");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 7;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		currentTimes.setFont(bigfont);
		available.add(currentTimes, gbc);
		
		for (int i = 0; i < days.length; i++) {
			JLabel day = new JLabel("<html>" + days[i] + "</html>");
			gbc = new GridBagConstraints();
			gbc.gridx = i;
			gbc.gridy = 1;
			gbc.weightx = 1;
			gbc.weighty = 100;
			gbc.anchor = GridBagConstraints.PAGE_START;
			available.add(day, gbc);
			labels.add(day);
		}
				
		add(scrollAvailable);
		scrollAvailable.setBounds(150, 200, 635, 360);
		scrollAvailable.setBorder(new LineBorder(Color.RED));
	}
	
	public void addTime(int day, int start, int end) {
		String time = formatTime(start) + " - " + formatTime(end);
		JLabel label = labels.get(day);
		label.setText(label.getText().substring(0, label.getText().length() - 7) + "<br>" + time + "</html>");
	}
	
	public String formatTime(int time) {
		String t2 = Integer.toString(time);
		if (time < 1000) {
			return t2.substring(0, 1) + ":" + t2.substring(1, 3);
		}
		else {
			return t2.substring(0, 2) + ":" + t2.substring(2, 4);
		}
	}
	
	public static void main(String[] args) {
		int w = 800;
		int h = 600;
		
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.add(new TimeSelectionPanel(), null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Time Selection");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(w, h);
		frame.setMinimumSize(new Dimension(w, h));
	}
}
