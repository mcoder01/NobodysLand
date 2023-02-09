package com.mcoder.nobodysland.gui;

import com.mcoder.j2dge.scene.View;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.view.Item;
import com.mcoder.nobodysland.view.TextureConstants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Inventory extends ArrayList<InventoryItem>
		implements View, KeyListener, MouseMotionListener, MouseWheelListener {
	private static final int maxLength = 6;

	private final int cellSize;
	private int selected;
	private boolean hovered;

	public Inventory(int cellSize) {
		super(maxLength);
		this.cellSize = cellSize;
	}

	@Override
	public void update() {
	}

	@Override
	public void show(Graphics2D g2d) {
		Composite oldComposite = g2d.getComposite();
		if (hovered)
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		int x = getX(), y = getY();
		g2d.drawImage(TextureConstants.INVENTORY.getTexture().getImage(), x, y, getWidth(), getHeight(), null);

		for (int i = 0; i < size(); i++) {
			int itemX = x + i * cellSize + 10;
			int itemY = y + 10;
			g2d.drawImage(get(i).getItem().getImage(), itemX, itemY, cellSize - 20, cellSize - 20, null);

			if (get(i).getQuantity() >= 0) {
				g2d.setColor(Color.WHITE);
				g2d.setFont(Font.decode("Arial-BOLD-16"));
				g2d.drawString("x" + get(i).getQuantity(), itemX + cellSize - 40, itemY + cellSize - 20);
			}
		}

		int selectorX = x + selected * cellSize - 2;
		int selectorY = y - 2;
		g2d.drawImage(TextureConstants.INVENTORY_SELECTOR.getTexture().getImage(), selectorX, selectorY, cellSize + 4, cellSize + 4, null);

		if (hovered)
			g2d.setComposite(oldComposite);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar())) {
			int digit = Character.getNumericValue(e.getKeyChar());
			if (digit >= 1 && digit <= maxLength)
				selected = digit - 1;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		hovered = e.getX() >= getX() && e.getY() >= getY() && e.getX() <= getX() + getWidth()
				&& e.getY() <= getY() + getHeight();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		selected += (int) e.getPreciseWheelRotation();
		if (selected == -1)
			selected = maxLength - 1;
		else if (selected == maxLength)
			selected = 0;
	}

	public Item pickSelectedItem() {
		return (selected < size()) ? get(selected).pick() : null;
	}

	public boolean add(InventoryItem item) {
		for (InventoryItem i : this)
			if (i.getItem() == item.getItem()) {
				i.stack(item.getQuantity());
				return true;
			}

		return super.add(item);
	}

	private int getX() {
		return Window.width / 2 - getWidth() / 2;
	}

	private int getY() {
		return Window.height - getHeight() - 30;
	}

	private int getWidth() {
		return cellSize * maxLength;
	}

	private int getHeight() {
		return cellSize;
	}
}