import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TimeSelectionCanvas extends JPanel implements MouseListener, MouseMotionListener  {
	
	private static final Font dayFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private static final Font timeFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private static String[] times = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00",
			"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
	
	private int width;
	private int height;
	
	private boolean[][] timeGrid;
	private boolean switchTo = true;
	
	private BufferedImage back;
		
	private int leftMargin;
	private int rightMargin;
	private int topMargin;
	private int bottomMargin;
	
	private int boxXSize;
	private int boxYSize;
		
	public TimeSelectionCanvas(int w, int h, int left, int right, int top, int bot) {
		width = w;
		height = h;
		leftMargin = left;
		rightMargin = right;
		topMargin = top;
		bottomMargin = bot;
		
		boxXSize = (width - leftMargin - rightMargin) / 7;
		boxYSize = (height - topMargin - bottomMargin) / 28;
				
		timeGrid = new boolean[28][7];

		StartProgram.socket.sendMessage("GETTIME " + StartProgram.username);
		String allTimes = StartProgram.socket.receiveMessage();
		int start, end, startIndex, endIndex;
		String[] startEnd, times;
		if (!allTimes.contentEquals("None")) {
			String[] days = allTimes.split(";");
			for (int i = 0; i < 7; i++) {
				times = days[i].substring(4).split(",");
				for (String time : times) {
					if (!time.equals("")) {
						startEnd = time.split("-");
						start = Integer.parseInt(startEnd[0]) % 10000;
						end = Integer.parseInt(startEnd[1]) % 10000;
						startIndex = start/100 * 2 + (start%100)/30 - 16;
						endIndex = end/100 * 2 + (end%100)/30 - 16;
						
						for (int j = startIndex; j < endIndex; j++) {
							timeGrid[j][i] = true;
						}
					}
				}
			}
		} 
		
		
		setLayout(null);
		JButton sendButton = new JButton("Save My Preferences");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int hour = 8;
				String minute = "00";
				int curHourStart = 8;
				String curMinuteStart = "00";
				boolean onStreak = false;
				String message = "";
				
				for (int i = 0; i < 7; i++) {
					message += days[i].substring(0, 3).toUpperCase() + ":";
					for (int j = 0; j < 28; j++) {
						boolean time = timeGrid[j][i];
						if (time) {
							if (!onStreak) {
								curHourStart = hour;
								curMinuteStart = minute;
								onStreak = true;
							}
						} else {
							if (onStreak) {
								onStreak = false;
								message += curHourStart + curMinuteStart + "-" + hour + minute + ",";
							}
						}
						
						if (minute.contentEquals("00")) {
							minute = "30";
						} else {
							minute = "00";
							hour++;
						}
					}
					
					if (onStreak)
						message += curHourStart + curMinuteStart + "-" + hour + minute + ",";
					
					if (message.charAt(message.length()-1) == ',') message = message.substring(0, message.length()-1);
					message += ";";
					
					hour = 8;
					minute = "00";
					curHourStart = 8;
					curMinuteStart = "00";
					onStreak = false;
				}
				
				StartProgram.socket.sendMessage("TIMEUPDATE " + StartProgram.username + " " + message);
				String result = StartProgram.socket.receiveMessage();
				if (result.contentEquals("SUCCESS")) {
					Point pos = TimeSelectionCanvas.this.getLocationOnScreen();
					Thread t = new Thread(new Toast("Preferences Saved!", 
							(int)(pos.getX() + w/2), (int)(pos.getY() + 0), 0.5));
					t.start();
				}
				else {
					Point pos = TimeSelectionCanvas.this.getLocationOnScreen();
					Thread t = new Thread(new Toast("An error occured!", 
							(int)(pos.getX() + w/2), (int)(pos.getY() + 0), 0.5));
					t.start();
				}
			}
		});
		
		
		add(sendButton);
		int buttonWidth = 220;
		int buttonHeight = 25;
		sendButton.setBounds(width - rightMargin - buttonWidth, height - bottomMargin + buttonHeight/2, buttonWidth, buttonHeight);
	}
	
	@Override
	public void paintComponent(Graphics w) {
		Graphics2D twoDGraph = (Graphics2D)w;
		if(back==null) back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics window = back.createGraphics();
		Graphics2D window2d = (Graphics2D) window;

		
		window2d.setColor(Color.WHITE);
		window2d.fillRect(0, 0, width, height);
		
		window2d.setColor(new Color(25, 160, 25));
		for (int i = 0; i < timeGrid.length; i++) {
			for (int j = 0; j < timeGrid[0].length; j++) {
				if (timeGrid[i][j]) {
					window2d.fillRect(leftMargin + boxXSize*j, topMargin + boxYSize*i, boxXSize, boxYSize);
				}
				else {
					window2d.setColor(Color.WHITE);
					window2d.fillRect(leftMargin + boxXSize*j, topMargin + boxYSize*i, boxXSize, boxYSize);
					window2d.setColor(new Color(25, 160, 25));
				}
			}
		}
		
		// Big Rectangle
		window2d.setColor(Color.BLACK);
		window2d.setStroke(new BasicStroke(4));
		window2d.drawRect(leftMargin, topMargin, width - leftMargin - rightMargin, height - topMargin - bottomMargin);
		
		// Vertical Lines
		window2d.setStroke(new BasicStroke(2));
		for (int i = 0; i < 7 * boxXSize; i+=boxXSize) {
			window2d.drawLine(leftMargin + i, topMargin, leftMargin + i, height - bottomMargin);
		}
		
		// Horizontal Lines
		for (int i = 0; i < 28 * boxYSize; i+=boxYSize) {
			if ((i/boxYSize)%2 == 0) window2d.setStroke(new BasicStroke(2));
			else window2d.setStroke(new BasicStroke(1));
			window2d.drawLine(leftMargin, topMargin + i, width - rightMargin, topMargin + i);
		}
		
		for (int i = 0; i < boxXSize * 7; i += boxXSize) {
			writeCentered(window2d, i + leftMargin + boxXSize/2, topMargin - 10, days[i/boxXSize], dayFont);
		}
		
		for (int i = 0; i < boxYSize * 28; i += boxYSize * 2) {
			writeRightAlign(window2d, leftMargin - 5, topMargin + i + 4, times[i/(boxYSize * 2)], timeFont);
		}
		
		twoDGraph.drawImage(back, null, 0, 0);
		repaint();
	}
	
	private void writeCentered(Graphics2D window2d, int x, int y, String text, Font f) {
		window2d.setFont(f);
    	int w = window2d.getFontMetrics().stringWidth(text);
    	window2d.drawString(text, x - w/2, y);
	}
	
	private void writeRightAlign(Graphics2D window2d, int x, int y, String text, Font f) {
		window2d.setFont(f);
    	int w = window2d.getFontMetrics().stringWidth(text);
    	window2d.drawString(text, x - w, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
		int xmargin = leftMargin + getX() + 8; // Canvas Margin + Position in Frame + random number
		int ymargin = topMargin + getY() + 30;
		
		int x = e.getX() - xmargin;
		int y = e.getY() - ymargin;
		if (x < 0 || y < 0) return;
		int gridx = (int)(x/boxXSize);
		int gridy = (int)(y/boxYSize);
		if (gridx < 0 || gridx >= timeGrid[0].length || gridy < 0 || gridy >= timeGrid.length) return;
		switchTo = !timeGrid[gridy][gridx];
		timeGrid[gridy][gridx] = switchTo;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int xmargin = leftMargin + getX() + 8; // Canvas Margin + Position in Frame + random number
		int ymargin = topMargin + getY() + 30;
		
		int x = e.getX() - xmargin;
		int y = e.getY() - ymargin;
		if (x < 0 || y < 0) return;
		int gridx = (int)(x/boxXSize);
		int gridy = (int)(y/boxYSize);
		if (gridx < 0 || gridx >= timeGrid[0].length || gridy < 0 || gridy >= timeGrid.length) return;
		timeGrid[gridy][gridx] = switchTo;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		int w = 1200;
		int h = 800;
		
//		try { 
//		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
		
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		
		JPanel placeholder = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 7;
		gbc.ipadx = 200;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		placeholder.setBorder(new LineBorder(Color.BLUE));
		frame.add(placeholder, gbc);
		
		JPanel placeholder2 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.ipadx = 600;
		gbc.ipady = 100;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		placeholder2.setBorder(new LineBorder(Color.RED));
		frame.add(placeholder2, gbc);
		
		TimeSelectionCanvas tsc = new TimeSelectionCanvas(1000, 700, 80, 80, 80, 60);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.ipadx = 990;
		gbc.ipady = 690;
//		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		frame.add(tsc, gbc);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(tsc);
		frame.addMouseMotionListener(tsc);
		frame.setTitle("Time Selection");
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
//		frame.setSize(w, h);
		
//		System.out.println(frame.getContentPane().getHeight());
//		System.out.println(frame.getContentPane().getWidth());
	}
}
