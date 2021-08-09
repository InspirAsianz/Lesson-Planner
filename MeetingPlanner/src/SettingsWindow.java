import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class SettingsWindow implements Runnable {

	@Override
	public void run() {
		int w = 500;
		int h = 400;

		JFrame frame = new JFrame();
		frame.setLayout(null);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Settings");
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
