package com.mcoder.nobodysland.view.sprite;

import com.mcoder.nobodysland.scene.View;
import com.mcoder.nobodysland.view.Game;

import java.awt.*;
import java.io.Serializable;

public class Wave implements View, Serializable {
	private final Enemy[] enemies;
	private long prevTime;
	private int timePerSpawn;
	private boolean attacking;
	private int currEnemy;

	public Wave(Enemy... enemies) {
		this.enemies = enemies;
	}

	public void startAttack(int spawnRate) {
		enemies[0].spawn(Game.player.getLevel().getPath());
		currEnemy = 1;
		attacking = true;

		timePerSpawn = (int) (1.0E3 / spawnRate);
		prevTime = System.currentTimeMillis();
	}

	@Override
	public void update() {
		if (!attacking)
			return;

		if (currEnemy < enemies.length) {
			long currTime = System.currentTimeMillis();
			if (currTime - prevTime >= timePerSpawn) {
				enemies[currEnemy++].spawn(Game.player.getLevel().getPath());
				prevTime = currTime;
			}
		}

		for (int i = 0; i < currEnemy; i++)
			enemies[i].update();
	}

	public void checkCollisions(Bullet bullet) {
		if (!attacking)
			return;
		for (Enemy enemy : enemies)
			if (bullet.hits(enemy)) {
				enemy.hurt(bullet.getPower());
				bullet.hurt(1);
			}
	}

	@Override
	public void show(Graphics2D g2d) {
		for (int i = 0; i < currEnemy; i++)
			enemies[i].show(g2d);
	}

	public Enemy getFirstLineEnemy() {
		for (int i = 0; i < currEnemy; i++)
			if (enemies[i].isAlive() && !enemies[i].offScreen())
				return enemies[i];
		return null;
	}

	public boolean isAttacking() {
		return attacking;
	}
}
