package com.mcoder.nobodysland.gui.menu;

import com.mcoder.j2dge.scene.Display;
import com.mcoder.j2dge.scene.Screen;
import com.mcoder.j2dge.scene.View;
import com.mcoder.nobodysland.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.LinkedList;

public abstract class GenericMenu extends Display implements KeyListener {
    private final LinkedList<View> views;
    private boolean closeable;

    protected GenericMenu() {
        super();
        views = new LinkedList<>();
    }

    public void addView(View v) {
        views.add(v);
        if (v instanceof EventListener)
            addListener((EventListener) v);
    }

    @Override
    public void update() {
        views.forEach(View::update);
    }

    @Override
    public void show(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 0.3f));
        g2d.fillRect(0, 0, Window.width, Window.height);
        views.forEach(v -> v.show(g2d));
    }

    public void close() {
        views.clear();
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
