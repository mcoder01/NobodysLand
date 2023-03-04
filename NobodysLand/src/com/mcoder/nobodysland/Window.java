package com.mcoder.nobodysland;

import com.mcoder.jge.screen.GameLoop;
import com.mcoder.jge.screen.Screen;
import com.mcoder.nobodysland.gui.menu.MainMenu;

public class Window {
	private static final String appName = "Nobody's Land";
	public static final int width = 540, height = 540;

	public static void main(String[] args) {
		Screen.getInstance().createWindow(appName, width, height);
		Screen.getInstance().addDrawer(new MainMenu());
		GameLoop gameLoop = new GameLoop(Screen.getInstance());
		gameLoop.start();
	}
}
