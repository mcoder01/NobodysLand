package com.mcoder.nobodysland.view.anim;

import java.util.HashMap;

public class KeyFrame extends HashMap<KeyFrame.Knob, Double> {
	private final int time;

	public KeyFrame(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public enum Knob {
		X, Y, SCALE, ROTATION, OPACITY;
	}
}
