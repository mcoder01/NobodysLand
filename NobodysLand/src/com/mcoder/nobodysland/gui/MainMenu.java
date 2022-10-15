package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.scene.Display;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

import java.awt.*;

public class MainMenu extends Display {
	private final Button makeLevel, newGame, continueGame;

	public MainMenu() {
		super();
		makeLevel = new Button("Create a new level", Window.width / 2 - 100, Window.height / 2 - 70, 200, 40);
		makeLevel.setOnClick(Game::makeNewLevel);
		add(makeLevel);

		newGame = new Button("Start a new game", Window.width / 2 - 100, Window.height / 2 - 20, 200, 40);
		Level firstLevel = ResourceManager.loadLevel(1);
		newGame.setOnClick(() -> Game.playLevel(firstLevel));
		newGame.setEnabled(firstLevel != null);
		add(newGame);

		continueGame = new Button("Continue", Window.width / 2 - 100, Window.height / 2 + 30, 200, 40);
		Level savedLevel = ResourceManager.loadLastSave(Game.user);
		continueGame.setOnClick(() -> Game.playLevel(savedLevel));
		continueGame.setEnabled(savedLevel != null);
		add(continueGame);
		onFocus();
	}

	@Override
	public void update() {}

	@Override
	public void show(Graphics2D g2d) {
		if (makeLevel == null || newGame == null || continueGame == null)
			return;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Window.width, Window.height);
		makeLevel.show(g2d);
		newGame.show(g2d);
		continueGame.show(g2d);
	}
}
