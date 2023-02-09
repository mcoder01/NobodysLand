package com.mcoder.nobodysland.gui.menu;

import com.mcoder.j2dge.scene.Screen;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Button;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;

public class MakerMenu extends GenericMenu {
	@Override
	public void prepare() {
		Button save = new Button("Save", Window.width / 2 - 110, Window.height / 2 - 15, 100, 30);
		save.setOnClick(() -> ResourceManager.saveLevel(Game.getMaker().getLevel()));
		save.setEnabled(!Game.getMaker().getLevel().getPath().isEmpty());
		addView(save);

		Button exit = new Button("Exit", Window.width / 2 + 10, Window.height / 2 - 15, 100, 30);
		exit.setOnClick(() -> {
			close();
			Screen.getInstance().removeDrawer(Game.getMaker());
			invoke(new MainMenu());
		});
		addView(exit);
	}
}
