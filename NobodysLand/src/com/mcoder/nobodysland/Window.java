package com.mcoder.nobodysland;

import com.mcoder.j2dge.scene.Screen;
import com.mcoder.nobodysland.gui.menu.GenericMenu;
import com.mcoder.nobodysland.gui.menu.MainMenu;

import javax.swing.*;

public class Window {
	private static final String appName = "Nobody's Land";
	public static final int width = 540, height = 540;

	public static void main(String[] args) {
		JFrame frame = new JFrame(appName);
		frame.add(Screen.getInstance());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		GenericMenu.invoke(new MainMenu());
		Screen.getInstance().start();
	}
}
