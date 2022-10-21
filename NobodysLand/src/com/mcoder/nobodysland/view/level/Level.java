package com.mcoder.nobodysland.view.level;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.scene.View;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.sprite.Enemy;
import com.mcoder.nobodysland.view.sprite.EntityType;
import com.mcoder.nobodysland.view.sprite.Wave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Level implements View, MouseListener, MouseMotionListener, Serializable {
	private int fileIndex;

	private final Spot[][] grid;
	private final int size;
	private Spot hovered;
	private final LinkedList<Spot> path;

	private final ArrayList<Wave> waves;
	private int currWave;
	private long prevTime;
	private final int attackDelay, spawnRate;
	private boolean highlighting, playing;

	public Level(int size) {
		super();
		this.size = size;
		grid = new Spot[size][size];

		double spotSize = getSpotSize();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				grid[i][j] = new Spot(i, j, spotSize, SpotContent.GRASS);

		path = new LinkedList<>();
		waves = new ArrayList<>();
		Enemy[] wave = new Enemy[10];
		for (int i = 0; i < wave.length; i++)
			wave[i] = new Enemy(EntityType.SOLDIER, 25, 25, 10);
		waves.add(new Wave(this, wave));
		attackDelay = 10000;
		spawnRate = 1;

		highlighting = true;
	}

	public void startGame() {
		prevTime = System.currentTimeMillis();
		currWave = 0;
		playing = true;
	}

	@Override
	public void update() {
		if (!playing)
			return;

		if (!getCurrentWave().isAttacking() && currWave < waves.size()) {
			long currTime = System.currentTimeMillis();
			if (currTime - prevTime >= attackDelay) {
				waves.get(currWave).startAttack(spawnRate);
				highlighting = false;
				prevTime = currTime;
			}
		}

		getCurrentWave().update();
	}

	public Spot findSpotUnder(int x, int y) {
		int row = (int) ((double) y / Window.height * size);
		int col = (int) ((double) x / Window.width * size);

		if (row < 0 || row >= size || col < 0 || col >= size)
			return null;

		return grid[row][col];
	}

	@Override
	public void show(Graphics2D g2d) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				grid[i][j].show(g2d);

		if (highlighting && hovered != null) {
			g2d.setColor(Color.BLACK);
			g2d.drawRect(hovered.getX(), hovered.getY(), (int) hovered.getSize(), (int) hovered.getSize());
		}

		if (playing)
			getCurrentWave().show(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!playing) return;

		Spot spot = findSpotUnder(e.getX(), e.getY());
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (spot.getFloor() == SpotContent.GRASS && spot.getGun() == null)
				Game.getPlayer().locateGun(spot);
		} else if (e.getButton() == MouseEvent.BUTTON3 && spot.getGun() != null)
			Game.getPlayer().removeGun(spot);
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		hovered = findSpotUnder(e.getX(), e.getY());
	}

	public int getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}
	
	public LinkedList<Spot> getPath() {
		return path;
	}

	public Wave getCurrentWave() {
		return waves.get(currWave);
	}

	public double getSpotSize() {
		return (double) Window.width / size;
	}

	public boolean isFinished() {
		return getCurrentWave().isFinished() && currWave == waves.size()-1;
	}
}
