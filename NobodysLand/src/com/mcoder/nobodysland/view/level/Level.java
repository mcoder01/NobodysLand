package com.mcoder.nobodysland.view.level;

import com.mcoder.jge.screen.View;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.view.sprite.Enemy;
import com.mcoder.nobodysland.view.sprite.EntityType;
import com.mcoder.nobodysland.view.sprite.Wave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Level implements View, MouseMotionListener, Serializable {
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

	public void setFloorAt(int row, int col, SpotContent floor) {
		if (floor == null || row >= grid.length || row < 0 || col >= grid[row].length ||  col < 0) return;
		grid[row][col].setFloor(floor);

		int[] desiredAnchorPoints = new int[4];
		if (row > 0 && (grid[row-1][col].getFloor() == SpotContent.WAY || grid[row-1][col].getFloor() == SpotContent.WAY_TURN))
			desiredAnchorPoints[0] = 1;
		else if (col <= grid[row].length-1 && (grid[row][col+1].getFloor() == SpotContent.WAY || grid[row][col+1].getFloor() == SpotContent.WAY_TURN))
			desiredAnchorPoints[1] = 1;
		else if (row <= grid.length-1 && (grid[row+1][col].getFloor() == SpotContent.WAY || grid[row+1][col].getFloor() == SpotContent.WAY_TURN))
			desiredAnchorPoints[2] = 1;
		else if (col > 0 && (grid[row][col-1].getFloor() == SpotContent.WAY || grid[row][col-1].getFloor() == SpotContent.WAY_TURN))
			desiredAnchorPoints[3] = 1;

		grid[row][col].match(desiredAnchorPoints);

		if (row > 0)
			grid[row-1][col].onNeighborChange(grid[row][col]); // Top

		if (col < grid[row].length-1)
			grid[row][col+1].onNeighborChange(grid[row][col]); // Right

		if (row < grid.length-1)
			grid[row+1][col].onNeighborChange(grid[row][col]); // Bottom

		if (col > 0)
			grid[row][col-1].onNeighborChange(grid[row][col]); // Left
	}

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

	public Spot getSpotAt(int row, int col) {
		return grid[row][col];
	}
}
