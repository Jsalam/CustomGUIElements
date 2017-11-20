package guiSet.lists;

import java.awt.Color;
import java.util.ArrayList;

import guiSet.buttons.Item;
import guiSet.elements.GuiElement;
import processing.core.*;
import processing.event.MouseEvent;

/**
 * Clickable list of items. The items are set by the setItems(Item[]) method.
 * Each Item is a button in the list. It has three show methods, two of them are
 * intended to display the target list with circular o rectangular nested
 * structure, i.e. lists that start empty and are filled by user selection of
 * items from the sourceList.
 * 
 * @author jsalam
 *
 */
public class CheckList extends GuiElement {

	private ArrayList<Item> itemList;
	private CheckList linkedList;
	private Item removedItem;
	private PVector pos;
	private PVector itemSize = new PVector(100, 17);
	public Color textColor = new Color(165, 199, 236);
	private int capacity = Integer.MAX_VALUE;
	PFont font;

	public CheckList(float x, float y, String name) {
		super();

		itemList = new ArrayList<Item>();

		pos = new PVector(x, y);

		this.name = name;

		// registerEvents();
	}

	/**
	 * Sets the pool of items to the collection of items
	 * 
	 * @param items
	 *            the items to be added
	 * @param enableSelectBehavior
	 *            true to enable selectedBehavior of items
	 */
	public void setItems(Item[] items, boolean enableSelectBehavior) {
		itemList.clear();
		capacity = items.length;
		for (int i = 0; i < items.length; i++) {
			items[i].setPosAndDimension(pos, itemSize, i);
			items[i].setEnableSelectBehavior(enableSelectBehavior);
			items[i].setNameVisibility(false);
			itemList.add(items[i]);
		}
	}

	public void setItems(String[] itemNames, boolean enableSelectBehavior) {
		itemList.clear();
		capacity = itemNames.length;
		setItems(makeItems(itemNames), enableSelectBehavior);
	}

	/**
	 * Builds a item array for each label
	 * 
	 * @param app
	 *            The PApplet
	 * @param labels
	 *            The array of labels
	 * @return The array of items
	 */
	public Item[] makeItems(String[] labels) {
		Item[] listItems = new Item[labels.length];
		for (int i = 0; i < labels.length; i++) {
			listItems[i] = new Item();
			listItems[i].setName(this.name + "_" + labels[i]);
			listItems[i].setLabel(labels[i]);
			listItems[i].setNameVisibility(false);
		}
		return listItems;
	}

	/**
	 * Plain show of the list of items
	 */
	public void show(PApplet app) {
		if (isVisible()) {
			app.fill(textColor.getRGB());
			if (isNameVisible())
				app.text(name, pos.x + 2, pos.y - 2);
			app.stroke(155);
			app.strokeWeight(1);
			app.line(pos.x, pos.y, pos.x + itemSize.x, pos.y);
			for (int i = 0; i < itemList.size(); i++) {
				itemList.get(i).show(app);
			}
		}
	}

	/**
	 * Show the list of items with an arc displaying the inverted order of
	 * items. Specially intended for target lists
	 */
	public void showCircular(PApplet app) {
		app.fill(textColor.getRGB());
		if (isLabelVisible())
			app.text(label, pos.x + 2, pos.y - 2);
		app.stroke(155);
		app.strokeWeight(1);
		app.line(pos.x, pos.y, pos.x + itemSize.x, pos.y);

		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).show(app);
			app.fill(255, 30);
			app.noStroke();
			app.arc(pos.x - 2, pos.y + (itemSize.y * (itemList.size())), (itemSize.y * 2) * (i + 1),
					(itemSize.y * 2) * (i + 1), -PApplet.HALF_PI, 0);
		}
	}

	/**
	 * Show the list of items with a rectangle displaying the inverted order of
	 * items. Specially intended for target lists
	 */
	public void showRectangular(PApplet app) {
		app.fill(textColor.getRGB());
		if (isNameVisible())
			app.text(name, pos.x + 2, pos.y - 2);
		app.stroke(155);
		app.strokeWeight(1);
		app.line(pos.x, pos.y, pos.x + itemSize.x, pos.y);

		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).show(app);
		}

		for (int i = 0; i < itemList.size(); i++) {
			app.fill(255, 30);
			app.noStroke();
			app.rect(pos.x + 2, 2 + pos.y + (itemSize.y * itemList.size()), itemSize.y + (i + 1) * 7,
					-itemSize.y * (i + 1));
			app.fill(255, 0, 0, 150);
			app.text("x", pos.x + itemSize.x - 7, pos.y + (itemSize.y * (i + 1)));
		}
	}

	/**
	 * Removes the reference of the clicked item from the collection of items.
	 * The item remains 'alive' at the pool of items
	 * 
	 * @param temp
	 *            the item to be removed
	 * 
	 * @return true if removed
	 */
	public boolean removeItem(Item temp) {
		boolean rtn = false;
		if (temp != null) {
			itemList.remove(temp);
			arrangeItemPositions();
			removedItem = temp;
			rtn = true;
		}
		return rtn;
	}

	/**
	 * Adds a reference from the collection of items to the pool of predefined
	 * items
	 * 
	 * @param receivedItem
	 *            the item to be added
	 */
	public void addItem(Item receivedItem) {
		if (receivedItem != null) {
			if (!itemList.contains(receivedItem) && itemList.size() < capacity) {
				receivedItem.setPosAndDimension(pos, itemSize, itemList.size() - 1);
				itemList.add(receivedItem);
				arrangeItemPositions();
			}
		}
	}

	private void arrangeItemPositions() {
		int count = 0;
		for (Item i : itemList) {
			i.setPosAndDimension(pos, itemSize, count);
			count++;

		}

	}

	public Item retrieveClickedItem(MouseEvent e) {
		Item temp = null;

		for (Item it : itemList) {
			if (it.isMouseOver(e)) {
				temp = it;
			}
		}
		if (temp == null) {
			System.out.println(this.getClass().getName() + " No item clicked from the list " + label);
		}

		return temp;
	}

	// ************* Getter and Setters *************
	public Item getRemovedItem() {
		return removedItem;
	}

	public CheckList getLinkedList() {
		return linkedList;
	}

	public PVector getItemSize() {
		return itemSize;
	}

	public void setLinkedList(CheckList linkedList) {
		this.linkedList = linkedList;
	}

	public void setItemSize(PVector itemSize) {
		this.itemSize = itemSize;
	}

	public void setItemColor(Color color) {
		for (Item it : itemList) {
			it.setBodyColor(color);
		}
	}

	/**
	 * Sets the maximum number of items of the target List
	 * 
	 * @param maxItems
	 *            maximum number of items
	 */
	public void setListCapacity(int maxItems) {
		capacity = maxItems;
	}

	/**
	 * Returns the list of item labels ordered by the user
	 * 
	 * @return the list of strings
	 */
	public String[] getOrderedLabelList() {
		String[] rtn = new String[itemList.size()];
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = itemList.get(i).getLabel();
		}
		return rtn;
	}

	/**
	 * Returns the list of selected items
	 * 
	 * @return the list of items
	 */
	public Item[] getSelected() {
		ArrayList<Item> rtn = new ArrayList<Item>();
		for (Item i : itemList) {
			if (i.isSelected())
				rtn.add(i);
		}
		Item[] array = new Item[rtn.size()];
		return rtn.toArray(array);
	}

	/**
	 * Returns the list of selected item labels
	 * 
	 * @return the list of strings
	 */
	public String[] getSelectedLabels() {
		Item[] selected = getSelected();
		String[] rtn = new String[selected.length];
		for (int i = 0; i < selected.length; i++) {
			rtn[i] = selected[i].getLabel();
		}
		return rtn;
	}

	public ArrayList<Item> getItems() {
		return itemList;
	}

	public String[] getItemLabels() {
		String[] rtn = new String[itemList.size()];
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = itemList.get(i).getName();
		}
		return rtn;
	}

	// ************* MouseEvent Methods & Observable *************
	public GuiElement[] getInternalGuiElements() {
		return itemList.toArray(new GuiElement[itemList.size()]);
	}

	public void mouseEvent(MouseEvent e) {
		if (isVisible()) {
			for (Item i : itemList) {
				i.mouseEvent(e);
			}
			if (e.getAction() == MouseEvent.CLICK) {
				mouseClicked(e);
			}
		}
	}

	private void mouseClicked(MouseEvent e) {

		if (isMouseOver(e)) {

			// If this is NOT a simple list
			if (linkedList != null) {
				switch (e.getButton()) {

				case PConstants.LEFT:

					// Observable
					notifyMyObservers(this);

					// control not to remove items from source if target is full
					if (linkedList.itemList.size() < linkedList.capacity) {
						removeItem(retrieveClickedItem(e));
					}
					linkedList.addItem(removedItem);
					removedItem = null;

					break;

				case PConstants.RIGHT:
					String[] listTemp = getOrderedLabelList();
					System.out.println(name + ":");
					for (int i = 0; i < listTemp.length; i++) {
						System.out.println(listTemp[i]);
					}
					break;
				}
			} else {

				// If a simple list
				switch (e.getButton()) {

				case PConstants.LEFT:

					// Observable
					setChanged();
					notifyObservers(this);

					break;

				case PConstants.RIGHT:
					String[] listTemp = getSelectedLabels();
					System.out.println(name + ":");
					for (int i = 0; i < listTemp.length; i++) {
						System.out.println(listTemp[i]);
					}
					break;
				}
			}
		}
	}

	/**
	 * Detects the mouse over the list
	 * 
	 * @return
	 */
	private boolean isMouseOver(MouseEvent e) {
		boolean rtn = false;
		if (itemList.size() > 0) {
			if (e.getX() > pos.x && e.getX() < pos.x + itemSize.x) {
				if (e.getY() > pos.y && e.getY() < pos.y + (itemList.size() * itemSize.y)) {
					rtn = true;
				}
			}
		} else {
			rtn = false;
		}
		return rtn;
	}
}
