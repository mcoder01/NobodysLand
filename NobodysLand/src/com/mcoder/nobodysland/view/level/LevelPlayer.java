package com.mcoder.nobodysland.view.level;

import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.Inventory;
import com.mcoder.nobodysland.gui.InventoryItem;
import com.mcoder.nobodysland.scene.Display;
import com.mcoder.nobodysland.scene.Screen;
import com.mcoder.nobodysland.view.Item;
import com.mcoder.nobodysland.view.Texture;
import com.mcoder.nobodysland.view.sprite.EntityType;
import com.mcoder.nobodysland.view.sprite.placeable.FixedGun;
import com.mcoder.nobodysland.view.sprite.placeable.Turret;

import java.awt.*;
import java.util.LinkedList;

public class LevelPlayer extends Display {
	private final Level level;
	private final Inventory inventory;

	private final LinkedList<FixedGun> guns;

	private final int playerHp;
	private int playerHpLeft;

	public LevelPlayer(Level level) {
		super();
		this.level = level;
		addListener(level);

		inventory = new Inventory(50);
		inventory.add(new InventoryItem(Item.TURRET, 3));
		addListener(inventory);

		guns = new LinkedList<>();
		playerHp = 15;
		playerHpLeft = playerHp;

		level.startGame();
		render();
	}

	@Override
	public void update() {
		level.update();

		for (FixedGun gun : guns)
			gun.update();

		if (!level.getCurrentWave().isAttacking())
			inventory.update();
		else Screen.getInstance().setTickSpeed(200);

		/*if (level.isFinished() && playerHpLeft > 0) {
			GenericMenu.invoke(new LevelFinishMenu());
			ResourceManager.saveData(Game.getUser(), level.getFileIndex() + 1);
			noLoop();
		}*/
	}

	public void locateGun(Spot spot) {
		if (level.getCurrentWave().isAttacking())
			return;

		Item item;
		if ((item = inventory.pickSelectedItem()) != null) {
			EntityType sprite = (EntityType) item.getGameObj();
			switch (sprite) {
				case TURRET -> {
					Turret turret = new Turret(spot, 2, playerHp, level);
					spot.setGun(turret);
					guns.add(turret);
				}
			}
		}
	}

	public void removeGun(Spot spot) {
		if (level.getCurrentWave().isAttacking())
			return;

		switch (spot.getGun().getType()) {
			case TURRET -> {
				inventory.add(new InventoryItem(Item.TURRET, 1));
				guns.remove(spot.getGun());
			}
		}

		spot.setGun(null);
	}

	@Override
	public void show(Graphics2D g2d) {
		level.show(g2d);

		for (FixedGun gun : guns)
			gun.show(g2d);

		float hpRatio = (float) playerHpLeft / playerHp;
		Composite oldComposite = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - hpRatio));
		g2d.drawImage(Texture.PLAYER_DAMAGE.getImage(), 0, 0, Window.width, Window.height, null);
		g2d.setComposite(oldComposite);

		if (!level.getCurrentWave().isAttacking())
			inventory.show(g2d);
	}

	public void hurt(int damage) {
		playerHpLeft -= damage;
		if (playerHpLeft < 0)
			playerHpLeft = 0;
		guns.forEach(fixedGun -> fixedGun.hurt(damage));
	}

	public Level getLevel() {
		return level;
	}

	public LinkedList<FixedGun> getGuns() {
		return guns;
	}
}
