package com.mcoder.nobodysland.view;

import com.mcoder.j2dge.view.sprite.Texture;
import com.mcoder.nobodysland.io.ResourceManager;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public enum Model {
    TURRET("entity/turret");
    // SOLDIER("entity/soldier");

    private final HashMap<String, Double> data;

    Model(String name) {
        data = ResourceManager.loadModel(name);
    }

    public BufferedImage[] apply(Texture texture) {
        int total = data.get("total").intValue();
        BufferedImage[] sliced = new BufferedImage[total];
        for (int i = 0; i < total; i++) {
            int x = data.get("x" + (i + 1)).intValue();
            int y = data.get("y" + (i + 1)).intValue();
            int w = data.get("w" + (i + 1)).intValue();
            int h = data.get("h" + (i + 1)).intValue();
            sliced[i] = texture.getImage().getSubimage(x, y, w, h);
        }

        return sliced;
    }

    public HashMap<String, Double> getData() {
        return data;
    }
}