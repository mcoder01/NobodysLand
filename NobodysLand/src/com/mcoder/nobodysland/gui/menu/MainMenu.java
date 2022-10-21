package com.mcoder.nobodysland.gui.menu;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Label;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.gui.widget.Button;

import java.awt.*;

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
			if (Game.getPlayer() != null)
				Screen.getInstance().removeDrawer(Game.getPlayer());
			Game.makeNewLevel();
		});
		addView(makeLevel);

		Button newGame = new Button("Start a new game", Window.width / 2 - 100, Window.height / 2 - 20, 200, 40);
		Level firstLevel = ResourceManager.loadLevel(1);
		newGame.setOnClick(() -> {
			close();
			ResourceManager.removeSave(Game.getUser());
			Screen.getInstance().removeDrawer(Game.getPlayer());
			Game.playLevel(firstLevel);
		});
		newGame.setEnabled(firstLevel != null);
		addView(newGame);

		Button continueGame = new Button("Continue", Window.width / 2 - 100, Window.height / 2 + 30, 200, 40);
		int savedLevel = ResourceManager.loadLastSave(Game.getUser());
		Level nextLevel = ResourceManager.loadLevel(savedLevel);
		continueGame.setOnClick(() -> {
			close();
			Screen.getInstance().removeDrawer(Game.getPlayer());
			Game.playLevel(nextLevel);
		});
		if (nextLevel == null) {
			continueGame.setEnabled(false);
			if (savedLevel > 1) {
				Label text = new Label("Nuovi livelli saranno presto disponibili!", Window.width / 2, Window.height - 50);
				text.setTextColor(Color.RED);
				text.center(true, true);
				addView(text);
			}
		}
		addView(continueGame);
	}
}
