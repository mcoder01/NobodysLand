package com.mcoder.nobodysland.scene;

import com.mcoder.nobodysland.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public class Screen extends Canvas {
	private static Screen instance;

	private final int tickSpeed, frameRate;
	private final LinkedList<Display> drawers;
	private Display toRemove, toAdd;

	private Screen() {
		super();
		setSize(Window.width, Window.height);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		drawers = new LinkedList<>();

		tickSpeed = 60;
		frameRate = 120;
	}

	public void start() {
		long lastTime = System.nanoTime();
		long unprocessedTicksTime = 0, unprocessedFramesTime = 0, totalTime = 0;
		double timePerTick = (tickSpeed == 0) ? 0 : 1.0E9 / tickSpeed;
		double timePerFrame = (frameRate == 0) ? 0 : 1.0E9 / frameRate;

		int ticks = 0, frames = 0;

		while (true) {
			long currTime = System.nanoTime();
			long passedTime = currTime - lastTime;
			totalTime += passedTime;
			unprocessedTicksTime += passedTime;
			unprocessedFramesTime += passedTime;
			lastTime = currTime;

			while (unprocessedTicksTime >= timePerTick) {
				tick();
				ticks++;
				if (tickSpeed > 0)
					unprocessedTicksTime -= timePerTick;
				else {
					unprocessedTicksTime = 0;
					break;
				}
			}

			while (unprocessedFramesTime >= timePerFrame) {
				draw();
				frames++;
				if (frameRate > 0)
					unprocessedFramesTime -= timePerFrame;
				else {
					unprocessedFramesTime = 0;
					break;
				}
			}

			if (totalTime >= 1.0E9) {
				System.out.println("Ticks: " + ticks + ", FPS: " + frames);
				totalTime = ticks = frames = 0;
			}
		}
	}

	private void tick() {
		for (Display drawer : drawers)
			drawer.update();
	}

	private void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		if (toRemove != null) {
			Display last = drawers.getLast();
			drawers.remove(toRemove);
			toRemove.onFocusLost();
			if (toRemove == last && drawers.size() > 0)
				drawers.getLast().onFocus();

			toRemove = null;
		}

		if (toAdd != null) {
			if (drawers.size() > 0)
				drawers.getLast().onFocusLost();

			drawers.add(toAdd);
			toAdd = null;
		}

		for (Display drawer : drawers)
			drawer.show(g2d);
		g2d.dispose();
		bs.show();
	}

	public void addDrawer(Display drawer) {
		toAdd = drawer;
	}

	public void removeDrawer(Display drawer) {
		toRemove = drawer;
	}

	public static Screen getInstance() {
		if (instance == null)
			instance = new Screen();
		return instance;
	}
}
