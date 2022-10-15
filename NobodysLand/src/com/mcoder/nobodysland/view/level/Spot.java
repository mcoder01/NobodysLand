package com.mcoder.nobodysland.view.level;

import com.mcoder.nobodysland.math.Vector;
import com.mcoder.nobodysland.scene.View;
import com.mcoder.nobodysland.view.sprite.placeable.FixedGun;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Spot implements View, Serializable {
	private final int row, col;
	private final double size;
	private int rotation;
	private SpotContent floor;
	private FixedGun gun;

	public Spot(int row, int col, double size, SpotContent content) {
		this.row = row;
		this.col = col;
		this.size = size;
		this.floor = content;
		gun = null;
	}

	@Override
	public void update() {}

	@Override
	public void show(Graphics2D g2d) {
		AffineTransform transform = new AffineTransform();
		BufferedImage image = floor.getTexture().getImage();
		double scale = size / image.getWidth();
		transform.translate(getX(), getY());
		transform.rotate(rotation * Math.PI / 2, size / 2, size / 2);
		transform.scale(scale, scale);
		g2d.drawImage(image, transform, null);
	}

	public void rotate() {
		if (floor.isRotatable())
			rotation = (rotation + 1) % 4;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getX() {
		return (int) (col * size);
	}

	public int getY() {
		return (int) (row * size);
	}

	public double getSize() {
		return size;
	}

	public int getRotation() {
		return rotation;
	}

	public void setFloor(SpotContent floor) {
		this.floor = floor;
	}

	public SpotContent getFloor() {
		return floor;
	}

	public Vector getCenter() {
		return new Vector(getX() + size / 2.0, getY() + size / 2.0);
	}

	public FixedGun getGun() {
		return gun;
	}

	public void setGun(FixedGun gun) {
		this.gun = gun;
	}
}
