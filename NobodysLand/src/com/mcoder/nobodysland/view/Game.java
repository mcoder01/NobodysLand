package com.mcoder.nobodysland.view;

import com.mcoder.nobodysland.gui.MainMenu;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.view.level.LevelMaker;
import com.mcoder.nobodysland.view.level.LevelPlayer;

public class Game {
	private static MainMenu menu;
	public static LevelMaker maker;
	public static LevelPlayer player;
	public static final String user = "player01";
	
	public static void showMenu() {
		menu = new MainMenu();
	}

	public static void makeNewLevel() {
		Screen.getInstance().removeDrawer(menu);
		maker = new LevelMaker(15);
	}

	public static void playLevel(Level level) {
		Screen.getInstance().removeDrawer(menu);
		if (level != null)
			player = new LevelPlayer(level);
	}
}
