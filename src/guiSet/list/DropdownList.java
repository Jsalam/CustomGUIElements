package guiSet.list;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import guiSet.elements.GuiElement;
import guiSet.elements.Item;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.MouseEvent;

public class DropdownList extends GuiElement {

	private Item cover, cover2;
	private RadioButtonList toggleList, toggleList2;
	private PVector pos;
	private PVector itemSize = new PVector(100, 17);
	public Color textColor = new Color(165, 199, 236);
	PFont font;

	public DropdownList(float x, float y, String name) {
		super();

		this.name = name;

		pos = new PVector(x, y);

		cover = new Item(new PVector(x, y + 2), itemSize);
		cover.setName("_" + name);
		cover.setLabel("List Names");
		cover.setNameVisibility(false);

		toggleList = new RadioButtonList(x + itemSize.x + 1, y, name + "_list");
		toggleList.setVisible(false);
		toggleList.setNameVisibility(false);

		cover2 = new Item(new PVector(x, y + 2 + itemSize.y), itemSize);
		cover2.setName("_" + name);
		cover2.setLabel("List Names 2");
		cover2.setNameVisibility(false);

		toggleList2 = new RadioButtonList(x + itemSize.x + 1, y + itemSize.y, name + "_list2");
		toggleList2.setVisible(false);
		toggleList2.setNameVisibility(false);

	}

	public void setListItems(String[] itemNames, boolean enableSelectBehavior) {
		toggleList.setItems(itemNames, enableSelectBehavior);
		toggleList2.setItems(itemNames, enableSelectBehavior);
	}

	@Override
	public void show(PApplet app) {

		if (isVisible()) {
			app.fill(textColor.getRGB());
			if (isNameVisible())
				app.text(name, pos.x + 2, pos.y - 2);
			app.stroke(155);
			app.strokeWeight(1);
			app.line(pos.x, pos.y, pos.x + itemSize.x, pos.y);

			cover.show(app);
			cover2.show(app);

			// List visible if its visibility is set to true. See mouseEvent
			// below
			toggleList.setVisible(cover.isSelected());
			toggleList.show(app);
			toggleList2.setVisible(cover2.isSelected());
			toggleList2.show(app);
		}
	}

	@Override
	public void mouseEvent(MouseEvent e) {
		cover.mouseEvent(e);
		toggleList.mouseEvent(e);

		cover2.mouseEvent(e);
		toggleList2.mouseEvent(e);

		if (e.getAction() == MouseEvent.CLICK) {

			if (cover.isMouseOver(e)) {
				cover2.reset();
			} else if (cover2.isMouseOver(e)) {
				cover.reset();
			}

			for (Item i : toggleList.getItems()) {
				if (i.isSelected() && i.isMouseOver(e)) {
					cover.setLabel(i.getLabel());
					cover.reset();
					cover2.reset();

					// Observable
					setChanged();
					notifyObservers(this);
					break;
				}
			}

			for (Item i : toggleList2.getItems()) {
				if (i.isSelected() && i.isMouseOver(e)) {
					cover2.setLabel(i.getLabel());
					cover2.reset();
					cover.reset();

					// Observable
					setChanged();
					notifyObservers(this);
					break;
				}
			}
		}
	}

	@Override
	public GuiElement[] getInternalGuiElements() {
				
		ArrayList<GuiElement> temp = new ArrayList<GuiElement>();
		temp.addAll(Arrays.asList(toggleList.getInternalGuiElements()));
		temp.addAll(Arrays.asList(toggleList2.getInternalGuiElements()));
		temp.add(toggleList);
		temp.add(toggleList2);
		return temp.toArray(new GuiElement[temp.size()]);
	}

}
