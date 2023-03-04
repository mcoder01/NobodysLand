package com.mcoder.nobodysland.gui.menu;

import com.mcoder.jge.screen.Screen;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.widget.Button;
import com.mcoder.nobodysland.view.Game;

public class LevelPauseMenu extends GenericMenu {
    public LevelPauseMenu() {
        super();
        setCloseable(true);
    }

    @Override
    public void prepare() {
        Button resume = new Button("Resume", Window.width/2-160, Window.height/2-20, 150, 40);
        resume.setOnClick(this::close);
        addView(resume);

        Button mainMenu = new Button("Main menu", Window.width/2+10, Window.height/2-20, 150, 40);
        mainMenu.setOnClick(() -> {
            close();
            Screen.getInstance().removeDrawer(Game.getPlayer());
            invoke(new MainMenu());
        });
        addView(mainMenu);
    }
}
