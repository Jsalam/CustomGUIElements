package guiSet.lists;

import java.awt.Color;

import guiSet.elements.GuiElement;
import processing.core.*;
import processing.event.MouseEvent;

/**
 * An instance of this GUI is composed by two lists. First, a source list
 * populated by items labeled with the Strings[] from the list furnished to the
 * constructor or set with the makeItems() method. Second, an empty target list
 * that receives the ordered items chosen by the user.
 * 
 * @author jsalam
 *
 */
public class OrderedCheckList extends GuiElement {
	private CheckList [] elements;
	private float listWidth = 100;
	private float listGap = 15;
	private PVector pos;
	private final int SOURCE = 0;
	private final int TARGET = 1;
	PFont font;

	/**
	 * Makes a ordered check list from a list of item labels
	 * 
	 * @param app
	 *            the PApplet
	 * @param x
	 *            Pos X
	 * @param y
	 *            Pos Y
	 * @param nameSource
	 *            Source list label
	 * @param nameTarget
	 *            Target list label
	 * @param labels
	 *            the set of item labels
	 */
	public OrderedCheckList(float x, float y, String nameSource, String nameTarget, String[] labels) {
		super();

		name = "List default name";

		pos = new PVector(x, y);
		
		elements = new CheckList[2];

		elements[SOURCE]= new CheckList(pos.x, pos.y + 33, nameSource);

		elements[SOURCE].setItems(labels, false);

		elements[TARGET] = new CheckList(pos.x + listWidth + listGap, pos.y + 33, nameTarget);

		elements[SOURCE].setLinkedList(elements[TARGET]);

		elements[TARGET].setLinkedList(elements[SOURCE]);

	}

	/**
	 * Makes an empty selectable
	 * 
	 * @param app
	 *            the PApplet
	 * @param x
	 *            Pos X
	 * @param y
	 *            Pos Y
	 * @param nameSource
	 *            Source list label
	 * @param nameTarget
	 *            Target list label
	 * 
	 */
	public OrderedCheckList(float x, float y, String nameSource, String nameTarget) {

		super();

		name = "List default name";

		pos = new PVector(x, y);
		
		elements = new CheckList[2];

		elements[SOURCE]= new CheckList(pos.x, pos.y + 33, nameSource);

		String[] labels = { "no labels yet" };

		setSourceItems(labels);

		elements[TARGET] = new CheckList(pos.x + listWidth + listGap, pos.y + 33, nameTarget);

		elements[SOURCE].setLinkedList(elements[TARGET]);

		elements[TARGET].setLinkedList(elements[SOURCE]);

	}

	/**
	 * Display the GUI on the PApplet
	 */
	public void show(PApplet app) {
		if (isVisible()) {
			app.fill(255);
			app.text(name, pos.x, pos.y + 10);
			elements[SOURCE].show(app);
			elements[TARGET].showRectangular(app);
		}
	}

	public void setVisible(boolean val) {
		super.setVisible(val);
		elements[SOURCE].setVisible(val);
		elements[TARGET].setVisible(val);
	}

	/**
	 * Makes and sets the source list items from a list of labels
	 * 
	 * @param labels
	 *            the list of labels
	 */
	public void setSourceItems(String[] labels) {
		elements[SOURCE].setItems(labels, false);
	}

	/**
	 * Set the color of both source and target list
	 * 
	 * @param color
	 *            The java.awt.Color
	 */
	public void setItemListColor(Color color) {
		elements[SOURCE].setItemColor(color);
		elements[TARGET].setItemColor(color);
	}

	/**
	 * Set the itemList size
	 * 
	 * @param x
	 *            The width
	 * @param y
	 *            The height
	 */
	public void setItemSize(float x, float y) {
		elements[SOURCE].setItemSize(new PVector(x, y));
		elements[TARGET].setItemSize(new PVector(x, y));
	}

	/**
	 * Sets the maximum capacity of the target list
	 * 
	 * @param maxItems
	 *            the target list capacity
	 */
	public void setMaxCapacityTargetList(int maxItems) {
		elements[TARGET].setListCapacity(maxItems);
	}

	/**
	 * Get the ordered list of items remaining at the Source list
	 * 
	 * @return the source list
	 */
	public String[] getOrderedSourceList() {
		return elements[SOURCE].getOrderedLabelList();
	}

	/**
	 * Get the ordered list of items remaining at the target list
	 * 
	 * @return the target list
	 */
	public String[] getOrderedTargetList() {
		return elements[TARGET].getOrderedLabelList();
	}
	
	public GuiElement[] getInternalGuiElements(){
		return elements;
	} 

	public void mouseEvent(MouseEvent e) {
		elements[SOURCE].mouseEvent(e);
		elements[TARGET].mouseEvent(e);
	}

}
