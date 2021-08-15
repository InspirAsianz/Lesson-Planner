import java.awt.*;
import javax.swing.*;


public class Toast extends JFrame implements Runnable {

	private static int allToasts = 0;
	private static final int MAX_TOASTS = 5;
	
	private String s;
	private JWindow w;
	private int xpos;
	private int ypos;
	private double seconds;

	public Toast(String s, int x, int y, double time)
	{
		seconds = time;
		xpos = x;
		ypos = y;
		this.s = s;
	}
	
	public void run() {
		if (allToasts >= MAX_TOASTS) {
			return;
		}
		allToasts++;
		w = new JWindow();

		// make the background transparent
		w.setBackground(new Color(0, 0, 0, 0));

		// create a panel
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight();

				// draw the boundary of the toast and fill it
				g.setColor(new Color(150, 150, 150));
				g.fillRect(10, 10, w + 30, h + 10);
				g.setColor(new Color(150, 150, 150));
				g.drawRect(10, 10, w + 30, h + 10);

				// set the color of text
				g.setColor(new Color(255, 255, 255, 240));
				g.drawString(s, 25, 27);
				int t = 250;

				// draw the shadow of the toast
				for (int i = 0; i < 4; i++) {
					t -= 60;
					g.setColor(new Color(0, 0, 0, t));
					g.drawRect(10 - i, 10 - i, w + 30 + i * 2, h + 10 + i * 2);
				}
			}
		};

		w.add(p);
		w.setLocation(xpos-150, ypos-50);
		w.setSize(300, 100);
		
		showToast();
	}

	public void showToast()
	{
		try {
			w.setOpacity(0.6f);
			w.setVisible(true);
			
			// make toast appear quickly
			for (double d = 0.6; d <= 1; d += 0.2) {
				Thread.sleep(25);
				w.setOpacity((float)d);
			}

			w.setOpacity(1);

			// wait for some time
			Thread.sleep((int)(seconds * 1000));

			// make the toast disappear slowly
			for (double d = 1.0; d > 0.2; d -= 0.025) {
				Thread.sleep(25);
				w.setOpacity((float)d);
			}

			// set the visibility to false
			w.setVisible(false);
			dispose();
			allToasts--;
		}
		catch (Exception e) {
			e.printStackTrace();
			allToasts--;
		}
	}
}
