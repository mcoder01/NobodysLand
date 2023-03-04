package com.mcoder.nobodysland.view.level;

import com.mcoder.jge.util.Texture;
import com.mcoder.nobodysland.view.Item;
import com.mcoder.nobodysland.view.TextureConstants;

public enum SpotContent {
	GRASS(TextureConstants.GRASS, new int[] {1, 1, 1, 1}, false),
	WAY(TextureConstants.WAY, new int[] {0, 1, 0, 1}, true),
	WAY_TURN(TextureConstants.WAY_TURN, new int[] {0, 1, 1, 0}, true),
	WATER(TextureConstants.WATER, new int[] {1, 1, 1, 1}, false);

	private final TextureConstants textureConstants;
	private final int[] anchorPoints;
	private final boolean rotatable;

	SpotContent(TextureConstants textureConstants, int[] anchorPoints, boolean rotatable) {
		this.textureConstants = textureConstants;
		this.anchorPoints = anchorPoints;
		this.rotatable = rotatable;
	}

	public Item toItem() {
		return Item.valueOf(toString());
	}

	public Texture getTexture() {
		return textureConstants.getTexture();
	}

	public int[] getAnchorPoints() {
		int[] ap = new int[4];
		System.arraycopy(anchorPoints, 0, ap, 0, 4);
		return ap;
	}

	public boolean isRotatable() {
		return rotatable;
	}
}