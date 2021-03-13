package week5;

import acm.gui.*;
import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import acm.program.GraphicsProgram;

public class BoxDiagram extends GraphicsProgram {

	/* Constants  */
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	private static double SPACER = 55;
		
	//Private instance variables
	private JTextField labelBox;
	private GObject gobj; 
	private GPoint last;
	private HashMap<String, GObject> labelList = new HashMap<String, GObject>();
	private double locY = 0; 
		
	public void init() {			
		locY = (getHeight() - BOX_HEIGHT)/2;
		labelBox = new JTextField(30);
		add(new JLabel("Name"), SOUTH);
		add((labelBox), SOUTH);
		labelBox.addActionListener(this);
			
		add(new JButton("Add"), SOUTH);
		add(new JButton("Remove"), SOUTH);
		add(new JButton("Clear"), SOUTH);	
			
		//Must call this method to get button press events
		addActionListeners();
		//Must call this method to be able to get mouse events
		addMouseListeners();			
	}
		
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) {
			createBox(labelBox.getText());
		} else if (cmd.equals("Remove")) {
			removeBox(labelBox.getText()); 
		} else if (cmd.equals("Clear")) {
			removeAll(); //Clears the canvas
			labelList.clear(); //Clears the list
			locY = (getHeight() - BOX_HEIGHT)/2;
		} 
	}
		
	private void createBox(String boxLabel) {
		GCompound box = new GCompound();
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(boxLabel);
		box.add(outline, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, (getWidth() - BOX_WIDTH)/2, locY);
		labelList.put(boxLabel, box);
		locY+= SPACER;
	}

	private void removeBox(String boxLabel) {
		GObject obj = labelList.get(boxLabel);
		if (obj != null) {
			remove(obj);
		}
	}
	/* Called on mouse press to record the coordinates of the click */
	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		gobj = getElementAt(last);
	}
		
	/* Called on mouse drag to reposition the object */
	public void mouseDragged(MouseEvent e) {
		if (gobj != null) {
			gobj.move(e.getX() - last.getX(),
			e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}
	
	/* Called on mouse click to move this object to the front */
	public void mouseClicked(MouseEvent e) {
		if (gobj != null) gobj.sendToFront();
	}
}
