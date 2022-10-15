package com.mcoder.nobodysland.view.sprite.placeable;

import com.mcoder.nobodysland.view.level.Level;
import com.mcoder.nobodysland.view.level.Spot;
import com.mcoder.nobodysland.view.sprite.EntityType;

public class Turret extends FixedGun {
	public Turret(Spot spot, int shootRate, int hp, Level level) {
		super(EntityType.TURRET, spot, shootRate, hp);
	}
}
