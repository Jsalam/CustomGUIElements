package guiSet;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import guiSet.elements.GuiElement;
import guiSet.elements.Item;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.MouseEvent;

public class GuiSet implements Observer {

	ArrayList<GuiElement> guiElements;
	PApplet app;

	PFont font;
	public String defaultFontName = ""; // "Helvetica"
	public GuiElement lastElementWithMouseEvent = null;

	public GuiSet(PApplet app) {
		super();

		this.app = app;

		guiElements = new ArrayList<GuiElement>();

		// Text vars
		if (checkInstalledFont(defaultFontName)) {
			font = app.createFont(defaultFontName, 11, false);
			app.textFont(font);
		}
	}

	public void addGuiElement(GuiElement ge) {
		
		if (guiElements.contains(ge)) {
			
			System.out.println("Element " + ge.getName() + " was not added to the gui set. Rename it");
		
		} else {

			// add observer to observable
			ge.addObserver(this);

			guiElements.add(ge);
			
			System.out.println("Element " + ge.getName() + " added to the gui set");
		}
	}

	public boolean removeGuiElement(String elementName) {
		
		GuiElement temp = null;

		for (GuiElement ge : guiElements) {
			if (ge.getName().equals(elementName)) {
				temp = ge;
			}
		}
		return guiElements.remove(temp);
	}

	public void show() {
		for (GuiElement ge : guiElements) {
			ge.show(app);
		}
	}

	public void show(PApplet app) {
		for (GuiElement ge : guiElements) {
			ge.show(app);
		}

	}

	public void setVisible(boolean val) {
		for (GuiElement ge : guiElements) {
			ge.setVisible(val);
		}
	}

	public static boolean checkInstalledFont(String fontName) {
		boolean exists = false;
		for (int i = 0; i < PFont.list().length; i++) {
			if (PFont.list()[i].equals(fontName))
				exists = true;
		}

		return exists;
	}

	/**
	 * Register mouse, touch or key events triggered on this object in the
	 * context of the canvas' PApplet. This must be call in the constructor of
	 * the class that instantiate GuiSet
	 * 
	 */
	public void registerGuiSetEvents(PApplet app) {
		app.registerMethod("mouseEvent", this);
	}

	/**
	 * Pass event captured on the guiSet to its guiElements
	 * 
	 * @param e
	 *            the mouseEvent
	 */
	public void mouseEvent(MouseEvent e) {

		// Pass the mouseEvent to the added GuiElements
		for (GuiElement ge : guiElements) {
			ge.mouseEvent(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		lastElementWithMouseEvent = (GuiElement) arg;
	}

	/**
	 * adds an observer to a guiElement identified by name
	 * 
	 * @param observer
	 *            the observer of the event on a gui element
	 * @param guiName
	 *            gui element name
	 */
	public void addObserverToGuiElement(Observer observer, String guiName) {
		GuiElement temp = null;
		for (GuiElement ge : guiElements) {
			if (ge.getName() != null && ge.getName().equals(guiName))
				temp = ge;
		}
		temp.addObserver(observer);

		// add Observer to internal elements
		if (temp.getInternalGuiElements() != null) {
			for (int i = 0; i < temp.getInternalGuiElements().length; i++) {
				temp.getInternalGuiElements()[i].addObserver(observer);

			}
		}
	}
}
