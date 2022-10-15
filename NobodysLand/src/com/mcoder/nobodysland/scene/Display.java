package com.mcoder.nobodysland.scene;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.EventListener;
import java.util.LinkedList;

public abstract class Display extends LinkedList<EventListener> implements EventListener, View {
	protected Display() {
		super();
		Screen.getInstance().addDrawer(this);
	}

	public void onFocus() {
		focus(this);
		forEach(this::focus);
	}

	public void onFocusLost() {
		forEach(this::unfocus);
		unfocus(this);
	}

	private void unfocus(EventListener l) {
		if (l instanceof MouseListener)
			Screen.getInstance().removeMouseListener((MouseListener) l);

		if (l instanceof MouseMotionListener)
			Screen.getInstance().removeMouseMotionListener((MouseMotionListener) l);

		if (l instanceof MouseWheelListener)
			Screen.getInstance().removeMouseWheelListener((MouseWheelListener) l);

		if (l instanceof KeyListener)
			Screen.getInstance().removeKeyListener((KeyListener) l);
	}

	private void focus(EventListener l) {
		if (l instanceof MouseListener)
			Screen.getInstance().addMouseListener((MouseListener) l);

		if (l instanceof MouseMotionListener)
			Screen.getInstance().addMouseMotionListener((MouseMotionListener) l);

		if (l instanceof MouseWheelListener)
			Screen.getInstance().addMouseWheelListener((MouseWheelListener) l);

		if (l instanceof KeyListener)
			Screen.getInstance().addKeyListener((KeyListener) l);
	}
}
