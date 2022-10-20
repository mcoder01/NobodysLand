package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

public class MainMenu extends GenericMenu {
	public MainMenu() {
		super();
		setCloseable(false);
	}

	@Override
	public void prepare() {
		Button makeLevel = new Button("Create a new level", Window.width / 2 - 100, Window.height / 2 - 70, 200, 40);
		makeLevel.setOnClick(() -> {
			close();
			Game.makeNewLevel();
		});
		addListener(makeLevel);

		Button newGame = new Button("Start a new game", Window.width / 2 - 100, Window.height / 2 - 20, 200, 40);
		Level firstLevel = ResourceManager.loadLevel(1);
		newGame.setOnClick(() -> {
			close();
			Game.playLevel(firstLevel);
		});
		newGame.setEnabled(firstLevel != null);
		addListener(newGame);

		Button continueGame = new Button("Continue", Window.width / 2 - 100, Window.height / 2 + 30, 200, 40);
		Level savedLevel = ResourceManager.loadLastSave(Game.getUser());
		continueGame.setOnClick(() -> {
			close();
			Game.playLevel(savedLevel);
		});
		continueGame.setEnabled(savedLevel != null);
		addListener(continueGame);
	}
}
