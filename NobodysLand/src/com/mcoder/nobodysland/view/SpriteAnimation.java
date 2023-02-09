package com.mcoder.nobodysland.view;

import com.mcoder.nobodysland.io.ResourceManager;

public enum SpriteAnimation {
    SOLDIER("entity/soldier");

    private final int frameRate, duration, length;

    SpriteAnimation(String name) {
        int[] data = ResourceManager.loadAnimation(name);
        frameRate = data[0];
        duration = data[1];
        length = data[2];
    }

    public int getFrameRate() {
        return frameRate;
    }

    public int getDuration() {
        return duration;
    }

    public int getLength() {
        return length;
    }
}