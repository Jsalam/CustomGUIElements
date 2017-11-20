package guiSet.example;

import java.util.Observable;
import java.util.Observer;

import guiSet.GuiSet;
import guiSet.buttons.Item;
import guiSet.buttons.PushButton;
import guiSet.elements.GuiElement;
import guiSet.lists.CheckList;
import guiSet.lists.DropdownList;
import guiSet.lists.OrderedCheckList;
import guiSet.lists.RadioButtonList;
import interfascia.GUIController;
import processing.core.*;
import texts.TextField;

public class Example extends PApplet implements Observer {

	GuiSet guiSet;
	OrderedCheckList selectableList;
	CheckList simpleList;
	Item item;
	PushButton button;
	DropdownList dropdown;
	RadioButtonList radioButtonList;
	GUIController controller;
	TextField textField;

	public void settings() {
		size(900, 400);
	}

	public void setup() {
		guiSet = new GuiSet(this);

		String[] labels = { "A", "B", "C", "D", "E", "A1", "B1", "C1", "D1", "E1" };

		// Instantiate the selectable list
		selectableList = new OrderedCheckList(100, 100, "Source", "Target", labels);
		selectableList.setItemSize(100, 12);
		selectableList.setName("Selectable List");

		// Instantiate the simple list
		simpleList = new CheckList(350, 100, "Simple List");
		simpleList.setItems(labels, true);

		// Item
		item = new Item(new PVector(100, 30), new PVector(150, 17));
		item.setName("Toggle");
		item.setLabel("click me");
		
		// Button
		button = new PushButton(new PVector(300, 30), new PVector(150, 17));
		button.setName("Push");
		button.setLabel("click me");

		// DropdownList
		dropdown = new DropdownList(500, 100, "Dropdown");
		dropdown.setListItems(labels, true);
		
		// Radiobutton
		radioButtonList = new RadioButtonList(750,100,"radiobutton");
		radioButtonList.setItems(labels,true);
		
		// TextField
		controller = new GUIController(this);
		textField = new TextField(controller);
		textField.setPosition(500,50);

		// add gui elements to this set of GUIs
		guiSet.addGuiElement(selectableList);
		guiSet.addGuiElement(simpleList);
		guiSet.addGuiElement(item);
		guiSet.addGuiElement(button);
		guiSet.addGuiElement(dropdown);
		guiSet.addGuiElement(radioButtonList);

		guiSet.registerGuiSetEvents(this);

		// Add observable elements to GuiSet observer
		guiSet.addObserverToGuiElement(this, "Toggle");
		guiSet.addObserverToGuiElement(this, "Push");
		guiSet.addObserverToGuiElement(this, "Simple List");
		guiSet.addObserverToGuiElement(this, "Selectable List");
		guiSet.addObserverToGuiElement(this, "Dropdown");
		guiSet.addObserverToGuiElement(this, "radiobutton");
	}

	public void draw() {
		background(70);
		guiSet.show();
	}

	/**
	 * The method to be triggered by guiElement event
	 * 
	 * @param ge
	 */
	public void printThis(GuiElement ge) {
		System.out.println("Example. mouseEvent on: " + ge.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		printThis((GuiElement) arg);
	}

	public static void main(String[] args) {
		PApplet.main("guiSet.example.Example");
	}
}
