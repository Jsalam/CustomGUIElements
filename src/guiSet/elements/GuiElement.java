package guiSet.elements;

import java.awt.Color;
import java.util.Observable;

import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Abstract Class containing the PApplet and some visual attributes of all Gui
 * elements
 * 
 * @author jsalam
 *
 */
public abstract class GuiElement extends Observable {

	// The label displayed
	protected String label;

	// The name of the element
	protected String name;

	protected Color labelColor = new Color(255, 255, 255, 200);

	protected Color nameColor = new Color(165, 199, 236);

	protected boolean visible = true;

	private boolean nameVisible = true;

	private boolean labelVisible = true;

	public abstract void show(PApplet app);

	/// ***** Events ******

	public abstract void mouseEvent(MouseEvent e);

	/// ***** Observable *****

	protected void notifyMyObservers(GuiElement ge) {
		setChanged();
		notifyObservers(ge);
	}

	public abstract GuiElement[] getInternalGuiElements();

	/// ***** Getters and Setters *****

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Color getLabelColor() {
		return labelColor;
	}

	public Color getNameColor() {
		return nameColor;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isLabelVisible() {
		return labelVisible;
	}

	public boolean isNameVisible() {
		return nameVisible;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
	}

	public void setNameColor(Color nameColor) {
		this.nameColor = nameColor;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (getInternalGuiElements() != null) {
			for (int i = 0; i < getInternalGuiElements().length; i++) {
				getInternalGuiElements()[i].setVisible(visible);
			}
		}
	}

	public void setLabelVisibility(boolean v) {
		labelVisible = v;
	}

	public void setNameVisibility(boolean v) {
		nameVisible = v;
	}

	public boolean equals(GuiElement ge) {
		return name.equals(ge.getName());
	}

	public int hascode() {
		return name.hashCode();
	}

}
