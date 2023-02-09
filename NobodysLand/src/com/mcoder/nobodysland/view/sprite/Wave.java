package com.mcoder.nobodysland.view.sprite;

import com.mcoder.j2dge.scene.View;
import com.mcoder.nobodysland.view.level.Level;

import java.awt.*;
import java.io.Serializable;

public class Wave implements View, Serializable {
	private final Level level;
	private final Enemy[] enemies;
	private int tickPerSpawn, tickCounter;
	private boolean attacking, finished;
	private int currEnemy;

	public Wave(Level level, Enemy[] enemies) {
		this.level = level;
		this.enemies = enemies;
	}

	public void startAttack(int spawnRate) {
		enemies[0].spawn(level.getPath());
		currEnemy = 1;
		attacking = true;
		tickPerSpawn = 60;
		tickCounter = 0;
	}

	@Override
	public void update() {
		if (!attacking)
			return;

		if (currEnemy < enemies.length) {
			tickCounter++;
			if (tickCounter == tickPerSpawn) {
				enemies[currEnemy++].spawn(level.getPath());
				tickCounter = 0;
			}
		} else {
			finished = true;
			for (int i = 0; i < currEnemy; i++) {
				if (enemies[i].isAlive() && !enemies[i].offScreen())
					finished = false;
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

	public boolean isFinished() {
		return finished;
	}
}
