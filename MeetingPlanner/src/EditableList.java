import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditableList extends JPanel {
		
	private int WIDTH;
	private int HEIGHT;
	
	private String[] items = {"Test1", "Test2", "Test3"};
	
	private String itemType = "Student";
	
	private DefaultListModel<String> nameListModel;
	private JList<String> nameList;
	private JTextField valueField;
	private JButton deleteButton;

	
	public EditableList (int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		setup();
	}
	
	public EditableList (int w, int h, String[] i, String it) {
		WIDTH = w;
		HEIGHT = h;
		items = i;
		itemType = it;
		setup();
	}
		
	private void setup() {
		setSize(WIDTH, HEIGHT);
		setBorder(null);
		setLayout(new GridBagLayout());

		nameListModel = new DefaultListModel<String>();
		for (String s : items) {
			nameListModel.addElement(s);
		} // http://www.seasite.niu.edu/cs580java/JList_Basics.htm
		
	    nameList = new JList<String>(nameListModel);
	    
	    nameList.setVisibleRowCount(HEIGHT/25-1);
	    nameList.setFixedCellWidth(WIDTH-25);
	    nameList.setFixedCellHeight(25);
	    nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
		Font displayFont = new Font("Sans Serif", Font.BOLD, 18);
		nameList.setFont(displayFont);
//		nameList.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				String selected = (String)nameList.getSelectedValue();
//				if (selected != null) {
//					valueField.setText(nameList.getSelectedValue().toString());
//				}
//				else {
//					valueField.setText("None");
//				}
//			}
//		});
		
		nameList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
		        	// Check if click is on a rectangle
		        	Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex()); 
		        	if (r != null && r.contains(evt.getPoint())) {
			            int index = list.locationToIndex(evt.getPoint());
			            String selected = list.getModel().getElementAt(index);
			            if (EditPersonWindow.currEditerPersonWindow != null) {
			            	EditPersonWindow.currEditerPersonWindow.dispose();
			            }
			            SwingUtilities.invokeLater(new EditPersonWindow(selected));
		        	}
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(nameList);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc);
		
//	    valueField = new JTextField("None", 10);
//	    valueField.setFont(displayFont);
//	    valueField.setEditable(false);
//	    
//	    gbc = new GridBagConstraints();
//		gbc.gridx = 0;
//		gbc.gridy = 1;
//		gbc.gridwidth = 1;
//		gbc.weightx = 1;
//		gbc.weighty = 9999;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.anchor = GridBagConstraints.NORTH;
//	    add(valueField, gbc);
//	    
//	    deleteButton = new JButton("View " + itemType);
//	    deleteButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
////            	int selectedIndex = nameList.getSelectedIndex();
////            	if (selectedIndex != -1) {
////            		nameListModel.remove(selectedIndex);
////            	}	
//            	String selectedPerson = nameList.getSelectedValue();
//            	SwingUtilities.invokeLater(new EditPersonWindow(selectedPerson));
//            }
//		});
//		gbc = new GridBagConstraints();
//		gbc.gridx = 0;
//		gbc.gridy = 2;
//		gbc.gridwidth = 1;
//		gbc.weightx = 1;
//		gbc.weighty = 9999;
////		gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.anchor = GridBagConstraints.NORTH;
//	    add(deleteButton, gbc);

//		setBorder(new LineBorder(Color.RED));
	}
	
	public static void main(String[] args) {
		int w = 800;
		int h = 600;
		
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setLayout(null);

		EditableList dlist = new EditableList(200, 400);
		
		frame.add(dlist, null);
		dlist.setBounds(25, 25, 200, 400);
				
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Scroll List Test");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
	}
	
}
