package com.mcoder.nobodysland.gui.menu;

import com.mcoder.jge.screen.Screen;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Button;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

public class LevelLostMenu extends GenericMenu {
    @Override
    public void prepare() {
        Button mainMenu = new Button("Main menu", Window.width/2-170, Window.height/2-20, 150, 40);
        mainMenu.setOnClick(() -> {
            close();
            Screen.getInstance().removeDrawer(Game.getPlayer());
            invoke(new MainMenu());
        });
        addView(mainMenu);

        Button newGame = new Button("Ricomincia", Window.width/2+20, Window.height/2-20, 150, 40);
        Level firstLevel = ResourceManager.loadLevel(1);
        newGame.setOnClick(() -> {
            close();
            Screen.getInstance().removeDrawer(Game.getPlayer());
            Game.playLevel(firstLevel);
        });
        addView(newGame);
    }
}
