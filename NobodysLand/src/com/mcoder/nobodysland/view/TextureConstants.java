package com.mcoder.nobodysland.view;

import com.mcoder.j2dge.view.sprite.Texture;
import com.mcoder.nobodysland.io.ResourceManager;

public enum TextureConstants {
	GRASS("floor/grass"), WAY("floor/way"), WAY_TURN("floor/way_turn"), WATER("floor/water"),
	INVENTORY("gui/inventory"), INVENTORY_SELECTOR("gui/inventory_selector"), BUTTON("gui/button", 400, 120),
	START_FLAG("level/start_flag"), FINISH_FLAG("level/finish_flag"), PLAYER_DAMAGE("level/player_damage"),
	BULLET("entity/bullet"), TURRET("entity/turret"), SOLDIER("entity/soldier"), EXPLOSION("anim/explosion");

	private final Texture texture;

	TextureConstants(String name) {
		texture = new Texture(ResourceManager.loadTexture(name));
	}

	TextureConstants(String name, int w, int h) {
		texture = new Texture(ResourceManager.loadTexture(name), w, h);
	}

	public Texture getTexture() {
		return texture;
	}
}