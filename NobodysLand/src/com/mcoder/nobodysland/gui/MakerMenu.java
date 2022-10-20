package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

public class MakerMenu extends GenericMenu {
	@Override
	public void prepare() {
		Button save = new Button("Save", Window.width / 2 - 110, Window.height / 2 - 15, 100, 30);
		save.setOnClick(() -> Level.saveNextLevel(Game.getMaker().getLevel()));
		save.setEnabled(!Game.getMaker().getLevel().getPath().isEmpty());
		addListener(save);

		Button exit = new Button("Exit", Window.width / 2 + 10, Window.height / 2 - 15, 100, 30);
		exit.setOnClick(() -> {
			close();
			Screen.getInstance().removeDrawer(Game.getMaker());
			Game.showMenu();
		});
		addListener(exit);
	}
}
