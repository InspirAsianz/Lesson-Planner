import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class AdminWindow implements Runnable {

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
		
		String[] studentsArray = {"Andrew", "Bob", "Joe", "Steve", "John", "Bohn", "Hohn", "joe gamer"};
		EditableList studentList = new EditableList(150, 600, studentsArray, "Student");
		panel.add(studentList);
		studentList.setBounds(250, 100, 150, 600);
		
		String[] teacherArray = {"Andrew", "Bob", "Joe", "Steve", "John", "Bohn", "Hohn", "joe gamer"};
		EditableList teacherList = new EditableList(150, 600, studentsArray, "Teacher");
		panel.add(teacherList);
		teacherList.setBounds(400, 100, 150, 600);


		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.addMouseListener(tsc);
//		frame.addMouseMotionListener(tsc);
		frame.setTitle("Lesson Planner Admin");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
		frame.setResizable(true);

	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new AdminWindow());
	}
}