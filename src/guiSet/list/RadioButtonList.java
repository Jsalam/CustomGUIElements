package guiSet.list;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import guiSet.elements.GuiElement;
import guiSet.elements.Item;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 * List of items for user single selection. The items are set by the
 * setItems(Item[]) method. Each Item is a button in the list.
 * 
 * @author jsalam
 *
 */
public class RadioButtonList extends GuiElement implements Observer {

	private ArrayList<Item> itemList;
	private Item selectedItem;
	private PVector pos;
	private PVector itemSize = new PVector(100, 17);
	public Color textColor = new Color(165, 199, 236);
	private int capacity = Integer.MAX_VALUE;
	PFont font;

	public RadioButtonList(float x, float y, String name) {
		super();

		itemList = new ArrayList<Item>();

		pos = new PVector(x, y);

		this.name = name;
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
			items[i].addObserver(this);
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
			listItems[i].setNameVisibility(false);
			listItems[i].setLabel(labels[i]);
			listItems[i].addObserver(this);
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
			for (Item i : itemList) {
				i.show(app);
			}
		}
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

	public PVector getItemSize() {
		return itemSize;
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
	 * Returns the selected item
	 * 
	 * @return the item
	 */
	public Item getSelected() {

		return selectedItem;
	}

	/**
	 * Returns the selected item label
	 * 
	 * @return the label strings
	 */
	public String getSelectedLabel() {

		return selectedItem.getLabel();
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

			if (e.getAction() == MouseEvent.CLICK) {

				for (Item i : itemList) {
					if (i.isMouseOver(e)) {
						selectedItem = i;
						break;
					}
				}

				for (Item i : itemList) {
					if (selectedItem != null && !i.equals(selectedItem)) {
						i.reset();
					}
				}
			}
			// Observable
			setChanged();
			notifyObservers(this);
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

	@Override
	public void update(Observable o, Object arg) {
		selectedItem = (Item) arg;
	}
}