package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.view.Item;

public class InventoryItem {
	private final Item item;
	private int quantity;

	public InventoryItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public InventoryItem(Item item) {
		this(item, -1);
	}

	public Item pick() {
		if (quantity == -1)
			return item;

		if (quantity > 0) {
			quantity--;
			return item;
		}

		return null;
	}

	public void stack(int quantity) {
		this.quantity += quantity;
	}

	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}
