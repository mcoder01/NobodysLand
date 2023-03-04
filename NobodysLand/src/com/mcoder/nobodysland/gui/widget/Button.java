package com.mcoder.nobodysland.gui.widget;

import com.mcoder.jge.screen.View;
import com.mcoder.nobodysland.view.TextureConstants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class Button implements View, MouseListener, MouseMotionListener {
	private final int width, height;
	private int x, y;
	private final Label label;
	private Runnable onClick;
	private boolean hovered, clicked, enabled;

	private final BufferedImage normal, active;

	{
		normal = TextureConstants.BUTTON.getTexture().getImage();
		active = TextureConstants.BUTTON.getTexture().getImage();
	}

	public Button(String text, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		label = new Label(text, x+width/2, y+height/2);
		label.center(true, true);
		enabled = true;
	}

	@Override
	public void update() {}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {}

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = enabled && e.getX() >= x && e.getY() >= y && e.getX() <= x + width && e.getY() <= y + height;
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if (clicked) {
			clicked = false;
			onClick.run();
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {}

	@Override
	public void show(Graphics2D g2d) {
		if (hovered)
			g2d.drawImage(active, x, y, width, height, null);
		else
			g2d.drawImage(normal, x, y, width, height, null);

		label.show(g2d);

		if (!enabled) {
			g2d.setColor(new Color(0, 0, 0, 0.5f));
			g2d.fillRect(x, y, width, height);
		}
	}

	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		hovered = e.getX() >= x && e.getX() <= x+width
			&& e.getY() >= y && e.getY() <= y+height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
