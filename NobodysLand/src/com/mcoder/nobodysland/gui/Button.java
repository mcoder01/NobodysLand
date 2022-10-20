package com.mcoder.nobodysland.gui;

import com.mcoder.nobodysland.scene.View;
import com.mcoder.nobodysland.view.Texture;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Button implements View, MouseListener {
	private final String label;
	private final int x, y, width, height;
	private Runnable onClick;
	private boolean clicked, enabled;

	private final BufferedImage normal, active;

	{
		normal = Texture.BUTTON.getImage().getSubimage(0, 0, 400, 120);
		active = Texture.BUTTON.getImage().getSubimage(0, 120, 400, 120);
	}

	public Button(String label, int x, int y, int width, int height) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		if (clicked)
			g2d.drawImage(active, x, y, width, height, null);
		else
			g2d.drawImage(normal, x, y, width, height, null);

		g2d.setFont(Font.decode("arial-REGULAR-18"));
		FontMetrics metrics = g2d.getFontMetrics();
		int textWidth = metrics.stringWidth(label);
		int textHeight = metrics.getHeight();

		g2d.setColor(Color.BLACK);
		g2d.drawString(label, x + width / 2 - textWidth / 2, y + height / 2 + textHeight / 4);

		if (!enabled) {
			g2d.setColor(new Color(0, 0, 0, 0.2f));
			g2d.fillRect(x, y, width, height);
		}
	}

	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
