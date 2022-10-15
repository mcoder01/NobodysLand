package com.mcoder.nobodysland.view.anim;

import com.mcoder.nobodysland.math.Vector;
import com.mcoder.nobodysland.view.sprite.Sprite;
import com.mcoder.nobodysland.view.anim.KeyFrame.Knob;

import java.util.HashMap;

public class Animator extends Thread {
	private final Animation animation;
	private final Sprite sprite;
	private int keyframeIndex;
	private final double timePerFrame;
	private HashMap<Knob, Double> steps;

	public Animator(Animation animation, Sprite sprite) {
		super();
		this.animation = animation;
		this.sprite = sprite;
		keyframeIndex = 0;
		timePerFrame = 1.0E3 / Animation.getFrameRate();
	}

	@Override
	public void run() {
		super.run();
		steps = new HashMap<>();
		int frameNum = calcStep();
		int frames = 0;
		long prevTime = System.currentTimeMillis();
		while (true) {
			long currTime = System.currentTimeMillis();
			if (currTime - prevTime >= timePerFrame) {
				update();
				frames++;
				if (frames == frameNum) {
					keyframeIndex++;
					if (keyframeIndex == animation.size() - 1)
						break;
					frames = 0;
					frameNum = calcStep();
				}
				prevTime = currTime;
			}
		}

		if (animation.getOnFinish() != null)
			animation.getOnFinish().run();
	}

	private void update() {
		sprite.getPos().add(new Vector(steps.get(Knob.X), steps.get(Knob.Y)));
		sprite.setScale(sprite.getScale() + steps.get(Knob.SCALE));
		sprite.setRotation(sprite.getRotation() + steps.get(Knob.ROTATION));
		sprite.setOpacity((float) (sprite.getOpacity() + steps.get(Knob.OPACITY)));
	}

	private int calcStep() {
		KeyFrame k1 = animation.get(keyframeIndex);
		KeyFrame k2 = animation.get(keyframeIndex + 1);
		double dt = (k2.getTime() - k1.getTime()) / timePerFrame;
		steps.clear();
		if (dt != 0)
			for (Knob knob : Knob.values())
				if (k1.containsKey(knob) && k2.containsKey(knob))
					steps.put(knob, (k2.get(knob) - k1.get(knob)) / dt);
				else
					steps.put(knob, 0.0);
		return (int) dt;
	}

	public static void launch(Animation animation, Sprite sprite) {
		Animator animator = new Animator(animation, sprite);
		animator.start();
	}
}
