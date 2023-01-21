package com.mcoder.nobodysland.gui.menu;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Label;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.gui.widget.Button;

import java.awt.*;

public class MainMenu extends GenericMenu {
	@Override
	public void prepare() {
		Button newGame = new Button("Start a new game", Window.width / 2 - 100, Window.height / 2 - 95, 200, 40);
		Level firstLevel = ResourceManager.loadLevel(1);
		newGame.setOnClick(() -> {
			close();
			ResourceManager.removeSave(Game.getUser());
			Game.playLevel(firstLevel);
		});
		newGame.setEnabled(firstLevel != null);
		addView(newGame);

		Button continueGame = new Button("Continue", Window.width / 2 - 100, Window.height / 2 - 45, 200, 40);
		int savedLevel = ResourceManager.loadLastSave(Game.getUser());
		Level nextLevel = ResourceManager.loadLevel(savedLevel);
		continueGame.setOnClick(() -> {
			close();
			Game.playLevel(nextLevel);
		});
		if (nextLevel == null) {
			continueGame.setEnabled(false);
			if (savedLevel > 1) {
				Label text = new Label("New levels coming soon!", Window.width / 2, Window.height - 50);
				text.setTextColor(Color.BLUE);
				text.center(true, true);
				addView(text);
			}
		}
		addView(continueGame);
		
		Button makeLevel = new Button("Create a new level", Window.width / 2 - 100, Window.height / 2 + 5, 200, 40);
		makeLevel.setOnClick(() -> {
			close();
			Game.makeNewLevel();
		});
		addView(makeLevel);
		
		Button exitGame = new Button("Exit", Window.width/2-100, Window.height/2+55, 200, 40);
		exitGame.setOnClick(() -> System.exit(0));
		addView(exitGame);
	}
}
