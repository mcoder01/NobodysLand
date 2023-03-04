package com.mcoder.nobodysland.view.sprite;

import com.mcoder.jge.util.Texture;
import com.mcoder.nobodysland.view.Model;
import com.mcoder.nobodysland.view.SpriteAnimation;
import com.mcoder.nobodysland.view.TextureConstants;

import java.awt.image.BufferedImage;

public enum EntityType {
	BULLET(TextureConstants.BULLET, null),
	TURRET(TextureConstants.TURRET, Model.TURRET),
	SOLDIER(TextureConstants.SOLDIER, null, null);

	private final TextureConstants textureConstants;
	private final Model model;
	private final SpriteAnimation animation;

	EntityType(TextureConstants textureConstants) {
		this(textureConstants, null, null);
	}

	EntityType(TextureConstants textureConstants, Model model) {
		this(textureConstants, model, null);
	}

	EntityType(TextureConstants textureConstants, Model model, SpriteAnimation animation) {
		this.textureConstants = textureConstants;
		this.model = model;
		this.animation = animation;
	}

	public BufferedImage[] model() {
		return model.apply(textureConstants.getTexture());
	}

	public Texture getTexture() {
		return textureConstants.getTexture();
	}

	public Model getModel() {
		return model;
	}

	public SpriteAnimation getAnimation() {
		return animation;
	}
}
