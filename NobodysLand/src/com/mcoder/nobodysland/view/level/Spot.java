package com.mcoder.nobodysland.view.level;

import com.mcoder.j2dge.math.Vector;
import com.mcoder.j2dge.scene.View;
import com.mcoder.nobodysland.view.sprite.placeable.FixedGun;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;

public class Spot implements View, Serializable {
	private final int row, col;
	private final double size;
	private int rotation;
	private int[] anchorPoints;
	private SpotContent floor;
	private FixedGun gun;

	public Spot(int row, int col, double size, SpotContent content) {
		this.row = row;
		this.col = col;
		this.size = size;
		this.floor = content;
		anchorPoints = content.getAnchorPoints();
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

	public void onNeighborChange(Spot neighbor) {
		if (floor == SpotContent.WAY && neighbor.floor == SpotContent.WAY) {
			int[] neighborsAnchorPoints = neighbor.floor.getAnchorPoints();
			int[] desiredAnchorPoints = new int[4];
			System.arraycopy(anchorPoints, 0, desiredAnchorPoints, 0, 4);

			int nr = neighbor.row;
			int nc = neighbor.col;

			if (nr == row-1 && nc == col && neighborsAnchorPoints[2] == 1) // Top
				desiredAnchorPoints[0] = 1;
			else if (nr == row && nc == col+1 && neighborsAnchorPoints[3] == 1) // Right
				desiredAnchorPoints[1] = 1;
			else if (nr == row+1 && nc == col && neighborsAnchorPoints[0] == 1) // Bottom
				desiredAnchorPoints[2] = 1;
			else if (nr == row && nc == col-1 && neighborsAnchorPoints[1] == 1)
				desiredAnchorPoints[3] = 1;

			match(desiredAnchorPoints);
		}
	}

	public void match(int[] desiredAnchorPoints) {
		int[] wayAnchorPoints = SpotContent.WAY.getAnchorPoints();
		int[] wayTurnAnchorPoints = SpotContent.WAY_TURN.getAnchorPoints();
		for (int i = 0; i < 3; i++) {
			if (Arrays.equals(wayAnchorPoints, desiredAnchorPoints)) {
				floor = SpotContent.WAY;
				rotation = i;
				break;
			} else rotateAnchorPoints(wayAnchorPoints);

			if (Arrays.equals(wayTurnAnchorPoints, desiredAnchorPoints)) {
				floor = SpotContent.WAY_TURN;
				rotation = i;
				break;
			} else rotateAnchorPoints(wayTurnAnchorPoints);
		}
	}

	private void rotateAnchorPoints(int[] anchorPoints) {
		int temp = anchorPoints[anchorPoints.length-1];
		for (int i = anchorPoints.length-1; i > 0; i--)
			anchorPoints[i] = anchorPoints[i-1];
		anchorPoints[0] = temp;
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
