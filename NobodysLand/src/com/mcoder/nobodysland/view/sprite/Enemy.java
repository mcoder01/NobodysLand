package com.mcoder.nobodysland.view.sprite;

import com.mcoder.jge.math.Vector;
import com.mcoder.jge.anim.*;
import com.mcoder.jge.g2d.Sprite;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.TextureConstants;
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
		explodeDamage = 5;
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
		explodeAnim = new Sprite(TextureConstants.EXPLOSION.getTexture(), pos.getX(), pos.getY(), 50, 50);
		Animation explosion = new Animation(200);
		Knob scaleKnob = ds -> explodeAnim.setScale(explodeAnim.getScale()+ds);
		Knob opacityKnob = dop -> explodeAnim.setOpacity((float) (explodeAnim.getOpacity()+dop));

		KeyFrame start = new KeyFrame(0);
		start.put(scaleKnob, 1.0);
		start.put(opacityKnob, 1.0);
		explosion.add(start);
		KeyFrame end = new KeyFrame(200);
		end.put(scaleKnob, 5.0);
		end.put(opacityKnob, 0.0);
		explosion.add(end);
		Animator.launch(explosion);
		Game.getPlayer().hurt(explodeDamage);
		hpLeft = 0;
	}

	public boolean offScreen() {
		return pos.getX() < -w / 2.0 || pos.getX() > Window.width + w / 2.0 || pos.getY() < -h / 2.0
				|| pos.getY() > Window.height + h / 2.0;
	}
}
