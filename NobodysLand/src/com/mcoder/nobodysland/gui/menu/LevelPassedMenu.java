package com.mcoder.nobodysland.gui.menu;

import com.mcoder.j2dge.scene.Screen;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Button;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.level.Level;

public class LevelPassedMenu extends GenericMenu {
    @Override
    public void prepare() {
        int x = Window.width/2-170;
        int levelIndex = Game.getPlayer().getLevel().getFileIndex();
        Level next = ResourceManager.loadLevel(levelIndex+1);
        if (next != null) {
            Button nextLevel = new Button("Next", Window.width/2+20, Window.height/2-20, 150, 40);
            nextLevel.setOnClick(() -> {
                close();
                Screen.getInstance().removeDrawer(Game.getPlayer());
                Game.playLevel(next);
            });
            addView(nextLevel);
        } else x = Window.width/2-75;

        Button mainMenu = new Button("Main menu", x, Window.height/2-20, 150, 40);
        mainMenu.setOnClick(() -> {
            close();
            Screen.getInstance().removeDrawer(Game.getPlayer());
            invoke(new MainMenu());
        });
        addView(mainMenu);
    }
}
