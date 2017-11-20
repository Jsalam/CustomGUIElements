package guiSet.buttons;

import java.awt.Color;

import guiSet.elements.GuiElement;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 * A rectangular element that behaves as a toggle button
 * 
 * @author jsalam
 *
 */
public class Item extends GuiElement {

	// Position and dimension
	private PVector org;
	private PVector dim;

	private float gap;
	// ***** Booleans *****
	private boolean mouseOver;
	private boolean selected = false;
	private boolean pressed = false;
	/*
	 * EnableSelectBehavior means that the item enables select and pressed
	 * behaviors, otherwise it only enables mouseOver behaviors
	 */
	private boolean enableSelectBehavior = true;

	private Color currentColor;
	private Color bodyColor = new Color(53, 53, 53);
	private Color overColor = new Color(3, 127, 222);
	private Color selectedColor = overColor.darker();

	PFont font;

	/**
	 * Constructor
	 * 
	 * @param app
	 *            The PApplet
	 */
	public Item() {
		super();

		// Set parameterized values
		// this.app = app;

		// Color vars
		currentColor = bodyColor;

		// Position vars
		gap = 2;
	}

	public Item(PVector listOrigin, PVector itemSize) {
		super();

		// Set parameterized values
		// this.app = app;

		// Position vars
		org = new PVector(listOrigin.x, listOrigin.y);
		dim = itemSize;

		// Color vars
		currentColor = bodyColor;
	}

	public void setFont(PFont font) {
		this.font = font;
	}

	public void setPosAndDimension(PVector listOrigin, PVector itemSize, int sequence) {
		setDimension(itemSize);
		setPosition(listOrigin, sequence);
	}

	public void setPosition(PVector listOrigin, int sequence) {
		if (dim != null)
			org = new PVector(listOrigin.x, listOrigin.y + gap + (dim.y * sequence));
		else
			org = new PVector(listOrigin.x, listOrigin.y + gap);
	}

	public void setPosition(PVector listOrigin) {
		org = new PVector(listOrigin.x, listOrigin.y);
	}

	public void setDimension(PVector itemSize) {
		dim = itemSize;
	}

	public void show(PApplet app) {
		if (isVisible()) {
			// Rectangle
			app.noStroke();
			app.fill(currentColor.getRGB());
			app.rect(org.x, org.y, dim.x, dim.y);

			// TopLine
			app.stroke(125, 100);
			app.strokeWeight(1);
			app.line(org.x, org.y, org.x + dim.x, org.y);

			// Label
			if (label != null && isLabelVisible()) {
				app.fill(labelColor.getRGB());
				app.text(label, org.x + 3, org.y + dim.y - 2);
			}

			// Name
			if (name != null && isNameVisible()) {
				app.fill(nameColor.getRGB());
				app.text(name, org.x + 2, org.y - 2);
			}
		}
	}

	/**
	 * This method is invoked every time there is a mouse event. See
	 * mouseEvent(MouseEvent e)
	 * 
	 * @return true if over
	 */
	public boolean isMouseOver(MouseEvent e) {
		mouseOver = false;
		// If the button is a rectangle
		if (dim.x != 0 && isVisible()) {
			// verifies the x range
			if (e.getX() > org.x && e.getX() < (org.x + dim.x)) {
				// verifies the y range
				if (e.getY() > org.y && e.getY() < (org.y + dim.y)) {
					mouseOver = true;
				}
			}
		}

		return mouseOver;
	}

	// ************* Color Methods *************

	private void colorManager() {
		// If mouse is over
		if (mouseOver) {
			// and selecteBehaviors enabled
			if (enableSelectBehavior) {
				// and is pressed
				if (pressed) {
					currentColor = currentColor.brighter();
				} else {
					// if not pressed but selected
					if (selected) {
						currentColor = selectedColor.darker();
					} else {
						// if not pressed nor selected
						currentColor = overColor;
					}
				}
			} else {
				// if over but selectBehaviors disabled
				currentColor = overColor;
			}
		} else {
			// if mouse not over but selectBehaviors enabled
			if (enableSelectBehavior) {
				// and selected
				if (selected) {
					currentColor = selectedColor;
				} else {
					// if not selected
					currentColor = bodyColor;
				}
			} else {
				// if mouse not over and selectedBehaviors disabled
				currentColor = bodyColor;
			}
		}
	}

	public void setAlpha(int alpha) {
		bodyColor = new Color(bodyColor.getRed(), bodyColor.getGreen(), bodyColor.getBlue(), alpha);
	}

	public int getAlpha() {
		return bodyColor.getAlpha();
	}

	public Color getColor() {
		return bodyColor;
	}

	public int getCurrentColorRGB() {
		return currentColor.getRGB();
	}

	public int setBodyColor(Color color) {
		this.bodyColor = color;
		return this.bodyColor.getRGB();
	}

	public int setBodyColor(int red, int green, int blue) {
		this.bodyColor = new Color(red, green, blue);
		return this.bodyColor.getRGB();
	}

	public int setBodyColor(int red, int green, int blue, int alpha) {
		this.bodyColor = new Color(red, green, blue, alpha);
		return this.bodyColor.getRGB();
	}

	public int setBodyColor(int brightness, int alpha) {
		this.bodyColor = new Color(brightness, brightness, brightness, alpha);
		return this.bodyColor.getRGB();
	}

	public String getLabel() {
		return label;
	}

	public boolean equals(Item val) {
		return name.equals(val.name);
	}

	// ******* Selected *******

	public void reset() {
		selected = false;
		pressed = false;
		currentColor = bodyColor;
	}

	public boolean isSelected() {
		return selected;
	}

	private void setSelected() {
		this.selected = !selected;
	}

	public boolean isEnableSelectBehavior() {
		return enableSelectBehavior;
	}

	public void setEnableSelectBehavior(boolean enableSelectBehavior) {
		this.enableSelectBehavior = enableSelectBehavior;
	}

	// ************* MouseEvent Methods & Observable *************

	public GuiElement[] getInternalGuiElements() {
		return null;
	}

	public void mouseEvent(MouseEvent e) {

		// handle event
		if (isVisible()) {

			if (e.getAction() == MouseEvent.MOVE) {
				isMouseOver(e);
			}
			if (e.getAction() == MouseEvent.CLICK) {
				mouseClicked(e);
			}

			if (e.getAction() == MouseEvent.PRESS) {
				mousePressed(e);
			}
			if (e.getAction() == MouseEvent.RELEASE) {
				mouseReleased(e);
			}

			// Manage color
			colorManager();
		}
	}

	private void mouseClicked(MouseEvent e) {

		if (isMouseOver(e)) {

			switch (e.getButton()) {
			case PConstants.LEFT:
				setSelected();

				// Observable
				if (isVisible()) {
					notifyMyObservers(this);
				}

				break;
			case PConstants.RIGHT:

				break;
			}
		}
	}

	private void mousePressed(MouseEvent e) {
		pressed = true;
	}

	private void mouseReleased(MouseEvent e) {
		pressed = false;
	}

}
