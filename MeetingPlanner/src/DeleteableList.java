import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DeleteableList extends JPanel {
		
	private int WIDTH;
	private int HEIGHT;
	
	private String[] items = {"Test1", "Test2", "Test3"};
	
	private String itemType = "Student";
	
	private DefaultListModel<String> nameListModel;
	private JList<String> nameList;
	private JTextField valueField;
	private JButton deleteButton;

	
	public DeleteableList (int w, int h) {
		WIDTH = w;
		HEIGHT = h;
		setup();
	}
	
	public DeleteableList (int w, int h, String[] i, String it) {
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
		} // http://www.seasite.niu.edu/cs580java/JList_Basics.htm lifesaver omg
		
	    nameList = new JList<String>(nameListModel);
	    
	    nameList.setVisibleRowCount(15);
	    nameList.setFixedCellWidth(WIDTH-25);
	    nameList.setFixedCellHeight(HEIGHT/20);
	    nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
		Font displayFont = new Font("Sans Serif", Font.BOLD, 18);
		nameList.setFont(displayFont);
		nameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selected = (String)nameList.getSelectedValue();
				if (selected != null) {
					valueField.setText(nameList.getSelectedValue().toString());
				}
				else {
					valueField.setText("None");
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(nameList);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		add(scrollPane, gbc);
		
	    valueField = new JTextField("None", 10);
	    valueField.setFont(displayFont);
	    valueField.setEditable(false);
	    
	    gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
	    add(valueField, gbc);
	    
	    deleteButton = new JButton("Remove " + itemType);
	    deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int selectedIndex = nameList.getSelectedIndex();
            	if (selectedIndex != -1) {
            		nameListModel.remove(selectedIndex);
            	}	
            }
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
	    add(deleteButton, gbc);

//		setBorder(new LineBorder(Color.RED));
	}
	
	public static void main(String[] args) {
		int w = 800;
		int h = 600;
		
		JFrame frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - w/2, dim.height/2 - h/2);
		frame.setLayout(null);

		DeleteableList dlist = new DeleteableList(200, 400);
		
		frame.add(dlist, null);
		dlist.setBounds(25, 25, 200, 400);
				
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Scroll List Test");
		frame.pack();
		frame.setSize(w, h);
		frame.setVisible(true);
	}
	
}
