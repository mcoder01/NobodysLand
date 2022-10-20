package com.mcoder.nobodysland.view.sprite;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.math.Vector;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.Texture;
import com.mcoder.nobodysland.view.anim.Animation;
import com.mcoder.nobodysland.view.anim.Animator;
import com.mcoder.nobodysland.view.anim.KeyFrame;
import com.mcoder.nobodysland.view.level.Spot;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Enemy extends Entity implements Serializable {
	private LinkedList<Spot> path;
	private final double maxSpeed;
	private final int explodeDamage;
	private int pathIndex;
	private boolean spawned;
	private Sprite explodeAnim;

	public Enemy(EntityType type, double w, double h, int hp) {
		super(type, 0, 0, w, h, hp);
		explodeDamage = 3;
		maxSpeed = 1;
	}

	@Override
	public void update() {
		if (!spawned)
			return;

		if (isAlive()) {
			pos.add(vel);
			if (pathIndex < path.size()-1) {
				Vector nextPoint = path.get(pathIndex).getCenter();
				if (Vector.dist(pos, nextPoint) < maxSpeed) {
					Vector currPoint = nextPoint;
					nextPoint = path.get(++pathIndex).getCenter();
					pos.set(currPoint);
					vel.set(Vector.sub(nextPoint, currPoint));
					vel.setMag(maxSpeed);
				}
			} else if (offScreen())
				explode();
		} else if (explodeAnim != null)
			explodeAnim.update();
	}

	@Override
	public void show(Graphics2D g2d) {
		if (!spawned)
			return;
		if (isAlive())
			super.show(g2d);
		else if (explodeAnim != null)
			explodeAnim.show(g2d);
	}

	public void spawn(LinkedList<Spot> path) {
		this.path = path;
		Vector v = new Vector(Game.getPlayer().getLevel().getSpotSize(), 0);
		pos.set(Vector.sub(path.getFirst().getCenter(), v));
		vel.set(v);
		vel.setMag(maxSpeed);
		pathIndex = 0;
		spawned = true;
	}

	private void explode() {
		Animation explosion = new Animation(200);
		KeyFrame start = new KeyFrame(0);
		start.put(KeyFrame.Knob.SCALE, 1.0);
		start.put(KeyFrame.Knob.OPACITY, 1.0);
		explosion.add(start);
		KeyFrame end = new KeyFrame(200);
		end.put(KeyFrame.Knob.SCALE, 5.0);
		end.put(KeyFrame.Knob.OPACITY, 0.0);
		explosion.add(end);
		explodeAnim = new Sprite(Texture.EXPLOSION, pos.getX(), pos.getY(), 50, 50);
		Animator.launch(explosion, explodeAnim);
		Game.getPlayer().hurt(explodeDamage);
		hpLeft = 0;
	}

	public boolean offScreen() {
		return pos.getX() < -w / 2.0 || pos.getX() > Window.width + w / 2.0 || pos.getY() < -h / 2.0
				|| pos.getY() > Window.height + h / 2.0;
	}
}
