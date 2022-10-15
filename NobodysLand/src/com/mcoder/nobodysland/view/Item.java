package com.mcoder.nobodysland.view;

import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.level.SpotContent;
import com.mcoder.nobodysland.view.sprite.EntityType;

import java.awt.image.BufferedImage;

public enum Item {
	GRASS("item/grass", SpotContent.GRASS), WAY("item/way", SpotContent.WAY),
	WAY_TURN("item/way_turn", SpotContent.WAY_TURN), WATER("item/water", SpotContent.WATER),
	TURRET("item/turret", EntityType.TURRET);

	private final BufferedImage image;
	private final Enum<?> gameObj;

	Item(String name, Enum<?> gameObj) {
		image = ResourceManager.loadTexture(name);
		this.gameObj = gameObj;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Enum<?> getGameObj() {
		return gameObj;
	}
}
