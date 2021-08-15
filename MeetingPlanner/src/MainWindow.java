import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class MainWindow implements Runnable {

	@Override
	public void run() {
		int w = 1200;
		int h = 800;
		
		JFrame frame = new JFrame();
		frame.setLayout(null);		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		frame.add(panel);
		panel.setBounds(0, 0, w, h);
		panel.setBorder(new LineBorder(Color.BLUE));
		
		TimeSelectionCanvas tsc = new TimeSelectionCanvas(1000, 800, 80, 80, 90, 94);
		
		
		
		panel.add(tsc);
		tsc.setBounds(200, 0, 1000, 800);
		tsc.setBorder(new LineBorder(Color.RED));
		
		
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(tsc);
		frame.addMouseMotionListener(tsc);
		frame.setTitle("Lesson Planner");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}
