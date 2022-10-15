package com.mcoder.nobodysland.view.sprite.placeable;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.math.Vector;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Spot;
import com.mcoder.nobodysland.view.sprite.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class FixedGun extends Entity {
	private final BufferedImage base, gun;
	private final Spot spot;
	private final double scale, gunAnchorX, gunAnchorY;

	private final LinkedList<Bullet> bullets;
	private Enemy target;
	private double gunRotation;
	private final double rotSpeed;

	private final int timePerShoot;
	private final int precision;
	private long prevTime;

	public FixedGun(EntityType type, Spot spot, float shootRate, int hp) {
		super(type, spot.getCenter().getX(), spot.getCenter().getY(), spot.getSize(), spot.getSize(), hp);
		this.spot = spot;

		BufferedImage[] sliced = type.model();
		base = sliced[0];
		gun = sliced[1];

		scale = spot.getSize() / base.getWidth();
		gunAnchorX = type.getModel().getData().get("gunAnchorX") * scale;
		gunAnchorY = type.getModel().getData().get("gunAnchorY") * scale;

		bullets = new LinkedList<>();
		rotSpeed = 0.5;
		precision = 45;

		timePerShoot = (int) (1.0E3 / shootRate);
		prevTime = System.currentTimeMillis();
	}

	@Override
	public void update() {
		if (isAlive()) {
			Wave wave = Game.player.getLevel().getCurrentWave();
			if (target == null && wave.isAttacking())
				// Find target enemy
				target = wave.getFirstLineEnemy();

			if (target != null) {
				// Follow target
				Vector forward = lookForward();
				gunRotation = aim(forward);

				if (targetAcquired(forward)) {
					long currTime = System.currentTimeMillis();
					if (currTime - prevTime >= timePerShoot) {
						// Shoot
						shoot();
						prevTime = currTime;
					}
				} else
					prevTime = System.currentTimeMillis();

				if (!target.isAlive() || target.offScreen())
					target = null;
			}
		}

		ArrayList<Bullet> toRemove = new ArrayList<>();
		for (Bullet bullet : bullets) {
			bullet.update();
			Game.player.getLevel().getCurrentWave().checkCollisions(bullet);
			if (!bullet.isAlive())
				toRemove.add(bullet);
		}

		bullets.removeAll(toRemove);
	}

	private Vector lookForward() {
		double dist = Vector.dist(spot.getCenter(), target.getPos());
		double maxDist = new Vector(Window.width, Window.height).mag();
		double off = dist / maxDist * precision;
		Vector forward = Vector.mult(target.getVel(), off);
		forward.add(target.getPos());
		return forward;
	}

	private double aim(Vector v) {
		Vector diff = Vector.sub(v, spot.getCenter());
		diff.setMag(rotSpeed);
		Vector vel = Vector.fromAngle(gunRotation);
		vel.add(diff);
		if (vel.mag() < 1) {
			if (vel.getX() == 0) {
				double ysq = Math.pow(vel.getY(), 2);
				vel.setX(Math.sqrt(1 - ysq));
			} else if (vel.getY() == 0) {
				double xsq = Math.pow(vel.getX(), 2);
				vel.setY(Math.sqrt(1 - xsq));
			}
		}
		vel.setMag(1);
		return vel.heading();
	}

	private void shoot() {
		Bullet bullet = new Bullet(pos.getX(), pos.getY(), 5, 1, 10);
		bullet.shoot(gunRotation);
		bullets.add(bullet);
	}

	private boolean targetAcquired(Vector v) {
		Vector diff = Vector.sub(v, spot.getCenter());
		double rot = gunRotation + Math.PI;
		double desiredAngle = diff.heading() + Math.PI;
		return Math.abs(rot - desiredAngle) <= rotSpeed;
	}

	@Override
	public void show(Graphics2D g2d) {
		super.show(g2d);
		g2d.drawImage(base, (int) (pos.getX() - w / 2), (int) (pos.getY() - h / 2), (int) w, (int) h, null);

		for (Bullet bullet : bullets)
			bullet.show(g2d);

		AffineTransform transform = new AffineTransform();
		int gunX = (int) (spot.getX() + spot.getSize() / 2 - gunAnchorX + 1);
		int gunY = (int) (spot.getY() + spot.getSize() / 2 - gunAnchorY + 1);
		transform.translate(gunX, gunY);
		transform.rotate(gunRotation, gunAnchorX, gunAnchorY);
		transform.scale(scale, scale);
		g2d.drawImage(gun, transform, null);
	}

	public float getHpLeftRatio() {
		return super.getHpLeftRatio();
	}
}
