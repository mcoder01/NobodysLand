package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.view.level.LevelMaker;
import com.mcoder.nobodysland.scene.Display;
import com.mcoder.nobodysland.scene.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MakerMenu extends Display implements KeyListener {
	private static MakerMenu instance;
	private final Button save, exit;

	private MakerMenu(LevelMaker maker) {
		super();
		save = new Button("Save", Window.width / 2 - 110, Window.height / 2 - 15, 100, 30);
		save.setOnClick(() -> Level.saveNextLevel(maker.getLevel()));
		save.setEnabled(!maker.getLevel().getPath().isEmpty());
		add(save);

		exit = new Button("Exit", Window.width / 2 + 10, Window.height / 2 - 15, 100, 30);
		exit.setOnClick(Game::showMenu);
		add(exit);
		onFocus();
	}

	@Override
	public void update() {
	}

	@Override
	public void show(Graphics2D g2d) {
		g2d.setColor(new Color(0, 0, 0, 0.5f));
		g2d.fillRect(0, 0, Window.width, Window.height);
		save.show(g2d);
		exit.show(g2d);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Screen.getInstance().removeDrawer(this);
			instance = null;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	public static void invoke(LevelMaker maker) {
		if (instance == null) {
			instance = new MakerMenu(maker);
			Screen.getInstance().addDrawer(instance);
		}
	}
}
