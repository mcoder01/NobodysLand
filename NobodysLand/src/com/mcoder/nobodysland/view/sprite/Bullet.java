package com.mcoder.nobodysland.view.sprite;

import com.mcoder.jge.math.Vector;
import com.mcoder.jge.g2d.Sprite;

import java.awt.*;

public class Bullet extends Entity {
	private final double radius;
	private final int speed;
	private final float power;

	public Bullet(double x, double y, double radius, float power, int speed) {
		super(EntityType.BULLET, x, y, radius * 2, radius * 2, 1);
		this.radius = radius;
		this.power = power;
		this.speed = speed;
	}

	@Override
	public void update() {
		super.update();
	}

	public void shoot(double angle) {
		vel.set(Vector.fromAngle(angle));
		vel.mult(speed);
	}

	public boolean hits(Sprite s) {
		return Vector.dist(pos, s.getPos()) < radius + s.getWidth();
	}

	@Override
	public void show(Graphics2D g2d) {
		g2d.drawImage(type.getTexture().getImage(), (int) (pos.getX() - w / 2), (int) (pos.getY() - h / 2), (int) w,
				(int) h, null);
	}

	public float getPower() {
		return power;
	}
}
