package com.mcoder.nobodysland.view;

import com.mcoder.nobodysland.io.ResourceManager;

import java.awt.image.BufferedImage;

public enum Texture {
	GRASS("floor/grass"), WAY("floor/way"), WAY_TURN("floor/way_turn"), WATER("floor/water"),
	INVENTORY("gui/inventory"), INVENTORY_SELECTOR("gui/inventory_selector"), BUTTON("gui/button"),
	PLAYER_DAMAGE("level/player_damage"),
	BULLET("entity/bullet"), TURRET("entity/turret"), SOLDIER("entity/soldier"), EXPLOSION("anim/explosion");

	private final BufferedImage image;

	Texture(String name) {
		image = ResourceManager.loadTexture(name);
	}

	public BufferedImage getImage() {
		return image;
	}
}