package com.mcoder.nobodysland.view;

import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.view.level.LevelMaker;
import com.mcoder.nobodysland.view.level.LevelPlayer;

public class Game {
	private static final String user = "player01";

	private static LevelMaker maker;
	private static LevelPlayer player;

	public static void makeNewLevel() {
		maker = new LevelMaker(15);
	}

	public static void playLevel(Level level) {
		if (level != null)
			player = new LevelPlayer(level);
	}

	public static String getUser() {
		return user;
	}

	public static LevelMaker getMaker() {
		return maker;
	}

	public static LevelPlayer getPlayer() {
		return player;
	}
}
