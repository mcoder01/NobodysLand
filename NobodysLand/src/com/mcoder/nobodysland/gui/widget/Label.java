package com.mcoder.nobodysland.gui.widget;

import com.mcoder.jge.screen.View;

import java.awt.*;

public class Label implements View {
    private int x, y;
    private String text;
    private Color textColor;
    private Font font;
    private boolean centerVertical, centerHorizontal;

    public Label(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;

        textColor = Color.WHITE;
        font = Font.decode("arial-REGULAR-18");
    }

    @Override
    public void update() {}

    @Override
    public void show(Graphics2D g2d) {
        g2d.setColor(textColor);
        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        g2d.drawString(text, (centerHorizontal) ? x-textWidth/2 : x, (centerVertical) ? y+textHeight/4 : y);
    }

    public void center(boolean centerVertical, boolean centerHorizontal) {
        this.centerVertical = centerVertical;
        this.centerHorizontal = centerHorizontal;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
