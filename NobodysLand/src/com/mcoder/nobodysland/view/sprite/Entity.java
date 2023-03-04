package com.mcoder.nobodysland.view.sprite;

import com.mcoder.jge.g2d.Sprite;

import java.awt.*;
import java.io.Serializable;

public class Entity extends Sprite implements Serializable {
	protected final EntityType type;
	protected final int hp;
	protected int hpLeft;

	public Entity(EntityType type, double x, double y, double w, double h, int hp) {
		super(type.getTexture(), x, y, w, h);
		this.type = type;
		this.hp = hp;
		hpLeft = hp;
	}

	@Override
	public void show(Graphics2D g2d) {
		super.show(g2d);
		// Draw the health bar
		int x = (int) (pos.getX() - w / 2);
		int y = (int) (pos.getY() - h / 2);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y - 10, (int) w, 4);
		g2d.setColor(new Color(0f, 0f, 0f, 0.5f));
		g2d.fillRect(x, y - 10, (int) w, 4);

		if (hpLeft >= hp / 2.0)
			g2d.setColor(Color.GREEN);
		else if (hpLeft >= hp / 4.0)
			g2d.setColor(Color.ORANGE);
		else
			g2d.setColor(Color.RED);
		int barWidth = (int) (getHpLeftRatio() * (w - 1));
		g2d.fillRect(x + 1, y - 9, barWidth, 3);
	}

	public void hurt(double damage) {
		hpLeft -= damage;
		if (hpLeft <= 0)
			hpLeft = 0;
	}

	public EntityType getType() {
		return type;
	}

	public boolean isAlive() {
		return hpLeft > 0;
	}

	protected float getHpLeftRatio() {
		return (float) hpLeft / hp;
	}
}
