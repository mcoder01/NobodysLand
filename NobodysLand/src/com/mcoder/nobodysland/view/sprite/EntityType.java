package com.mcoder.nobodysland.view.sprite;

import com.mcoder.nobodysland.view.anim.SpriteAnimation;
import com.mcoder.nobodysland.view.Model;
import com.mcoder.nobodysland.view.Texture;

import java.awt.image.BufferedImage;

public enum EntityType {
	BULLET(Texture.BULLET, null),
	TURRET(Texture.TURRET, Model.TURRET), 
	SOLDIER(Texture.SOLDIER, null, null);

	private final Texture texture;
	private final Model model;
	private final SpriteAnimation animation;

	EntityType(Texture texture) {
		this(texture, null, null);
	}

	EntityType(Texture texture, Model model) {
		this(texture, model, null);
	}

	EntityType(Texture texture, Model model, SpriteAnimation animation) {
		this.texture = texture;
		this.model = model;
		this.animation = animation;
	}

	public BufferedImage[] model() {
		return model.apply(texture);
	}

	public Texture getTexture() {
		return texture;
	}

	public Model getModel() {
		return model;
	}

	public SpriteAnimation getAnimation() {
		return animation;
	}
}
