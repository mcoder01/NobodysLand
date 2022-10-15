package com.mcoder.nobodysland.view.level;

import com.mcoder.nobodysland.view.Item;
import com.mcoder.nobodysland.view.Texture;

public enum SpotContent {
	GRASS(Texture.GRASS, false), WAY(Texture.WAY, true), WAY_TURN(Texture.WAY_TURN, true), WATER(Texture.WATER, false);

	private final Texture texture;
	private final boolean rotatable;

	SpotContent(Texture texture, boolean rotatable) {
		this.texture = texture;
		this.rotatable = rotatable;
	}

	public Item toItem() {
		return Item.valueOf(toString());
	}

	public Texture getTexture() {
		return texture;
	}

	public boolean isRotatable() {
		return rotatable;
	}
}