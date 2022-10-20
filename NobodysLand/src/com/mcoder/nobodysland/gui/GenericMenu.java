package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.scene.Display;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.scene.View;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class GenericMenu extends Display implements KeyListener {
    private boolean closeable;

    @Override
    public void update() {
        listeners.forEach(l -> {
            if (l instanceof View)
                ((View) l).update();
        });
    }

    @Override
    public void show(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 0.3f));
        g2d.fillRect(0, 0, Window.width, Window.height);
        listeners.forEach(l -> {
            if (l instanceof View)
                ((View) l).show(g2d);
        });
    }

    public void close() {
        Screen.getInstance().removeDrawer(this);
    }

    public abstract void prepare();

    @Override
    public void keyPressed(KeyEvent e) {
        if (closeable && e.getKeyCode() == KeyEvent.VK_ESCAPE)
            close();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public static void invoke(GenericMenu menu) {
        menu.prepare();
        Screen.getInstance().addDrawer(menu);
    }
}
