package TimeTableScheduler;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.html.*;

import com.mindfusion.common.*;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import java.util.ArrayList;

//set up the login page 
public abstract class Base extends JFrame
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				login window = null;
				try {
					window = new login();
					window.setVisible(true);
				}
				catch (Exception exp) {
				}
			}
		});
	}

	protected Base()
	{
		setDefaultCloseOperation(HIDE_ON_CLOSE);
                
		fileChooser = new JFileChooser();
		calendar = new Calendar();
		calendar.setCurrentTime(DateTime.now());
		
                //setting outline of calendar
		label = new JTextPane();
		label.setBackground(new Color(255, 255, 225));
		label.setBorder(new LineBorder(Color.orange, 1));
		label.setEditable(false);
		label.setEditorKit(new HTMLEditorKit());
		labelPane = new JScrollPane(label);
		
		content = new JPanel();
		content.setBackground(new Color(242, 242, 242));
		content.setLayout(new GridLayout(1, 1));
		Container cp = getContentPane();
		
                //create menubar
		JMenuBar menuBar = new JMenuBar();
                //menubar heading
		JMenu mFile = new JMenu("Menu");
                //menubar "Open" function
		JMenuItem mIFOpen = new JMenuItem("Open");
		mIFOpen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				openFileClicked();
			}
		});
		//menubar "Save" function
		JMenuItem mIFSave = new JMenuItem("Save");
		mIFSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				saveFileClicked();
			}
		});
                
		menuBar.add(mFile);
		mFile.add(mIFOpen);
		mFile.add(mIFSave);
		setJMenuBar(menuBar);
		
                
                
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.WEST, labelPane, 0, SpringLayout.WEST, cp);
		layout.putConstraint(SpringLayout.NORTH, labelPane, 0, SpringLayout.NORTH, cp);
		layout.putConstraint(SpringLayout.EAST, labelPane, 0, SpringLayout.EAST, cp);
		layout.putConstraint(SpringLayout.SOUTH, labelPane, 1, SpringLayout.NORTH, cp);

		layout.putConstraint(SpringLayout.WEST, content, 0, SpringLayout.WEST, cp);
		layout.putConstraint(SpringLayout.NORTH, content, 1, SpringLayout.NORTH, cp);
		layout.putConstraint(SpringLayout.EAST, content, 0, SpringLayout.EAST, cp);
		layout.putConstraint(SpringLayout.SOUTH, content, 0, SpringLayout.SOUTH, cp);
		
		cp.setLayout(layout);
		cp.add(labelPane);
		cp.add(content);
	}
	
        //when save button is clicked
	protected void saveFileClicked() {
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
                    //save to XML and obtain the path
                    calendar.getSchedule().saveTo(fileChooser.getSelectedFile().getAbsolutePath(), ContentType.Xml);
		}
	}
        
        // when file button is clicked in menu bar
	protected void openFileClicked() {
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{       //path for selecting file, and the file chooser menu
			calendar.getSchedule().loadFrom(fileChooser.getSelectedFile().getAbsolutePath(), ContentType.Xml);
			if (calendar.getGroupType() != GroupType.None)
			{   //collect all the necessary information on the calendar
				calendar.beginInit();
				calendar.getContacts().clear();
				calendar.getResources().clear();
				calendar.getLocations().clear();
				calendar.getTasks().clear();
				calendar.getContacts().addAll(calendar.getSchedule().getContacts());
				calendar.getResources().addAll(calendar.getSchedule().getResources());
				calendar.getLocations().addAll(calendar.getSchedule().getLocations());
				calendar.getTasks().addAll(calendar.getSchedule().getTasks());
				calendar.endInit();
			}
		}
	}


	private static final long serialVersionUID = 1L;

	Calendar calendar;
	JFileChooser fileChooser;
	JPanel content;
	JTextPane label;
	JScrollPane labelPane;
        Choice teachers;
        Choice subjects;
        Choice classes;
        ArrayList<Contact> contactsList;
        ArrayList<Task> subjectsList;
        JRadioButton btnMethod1;
	JRadioButton btnMethod2;
}
