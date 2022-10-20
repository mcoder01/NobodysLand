package com.mcoder.nobodysland.view.level;

import com.mcoder.nobodysland.gui.GenericMenu;
import com.mcoder.nobodysland.gui.Inventory;
import com.mcoder.nobodysland.gui.InventoryItem;
import com.mcoder.nobodysland.gui.MakerMenu;
import com.mcoder.nobodysland.scene.Display;
import com.mcoder.nobodysland.view.Item;

import java.awt.*;
import java.awt.event.*;

public class LevelMaker extends Display implements MouseListener, MouseMotionListener, KeyListener {
	private final Level level;
	private final Inventory inventory;

	public LevelMaker(int size) {
		super();

		level = new Level(size);
		addListener(level);

		inventory = new Inventory(50);
		inventory.add(new InventoryItem(Item.GRASS));
		inventory.add(new InventoryItem(Item.WAY));
		inventory.add(new InventoryItem(Item.WAY_TURN));
		inventory.add(new InventoryItem(Item.WATER));
		addListener(inventory);
		render();
	}

	@Override
	public void update() {}

	@Override
	public void show(Graphics2D g2d) {
		level.show(g2d);
		inventory.show(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Spot spot = level.findSpotUnder(e.getX(), e.getY());
		if (!changeSpotFloor(spot))
			spot.rotate();
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Spot spot = level.findSpotUnder(e.getX(), e.getY());
		changeSpotFloor(spot);
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent keyEvent) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			GenericMenu.invoke(new MakerMenu());
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {}

	private boolean changeSpotFloor(Spot spot) {
		if (spot != null) {
			Item item = inventory.pickSelectedItem();
			if (item != null && item != spot.getFloor().toItem()) {
				spot.setFloor((SpotContent) item.getGameObj());
				if (item == Item.WAY || item == Item.WAY_TURN)
					level.getPath().add(spot);
				else if (spot.getFloor() == SpotContent.WAY || spot.getFloor() == SpotContent.WAY_TURN)
					level.getPath().remove(spot);
				return true;
			}
		}

		return false;
	}

	public Level getLevel() {
		return level;
	}
}
