package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

public class LevelFinishMenu extends GenericMenu {
    @Override
    public void prepare() {
        Button nextLevel = new Button("Next", Window.width/2+20, Window.height/2-20, 150, 40);
        nextLevel.setOnClick(() -> Game.playLevel(Level.loadNextLevel()));
        addListener(nextLevel);

        Button mainMenu = new Button("Main menu", Window.width/2-170, Window.height/2-20, 150, 40);
        mainMenu.setOnClick(() -> {
            close();
            Screen.getInstance().removeDrawer(Game.getPlayer());
            Game.showMenu();
        });
        addListener(mainMenu);
    }
}
