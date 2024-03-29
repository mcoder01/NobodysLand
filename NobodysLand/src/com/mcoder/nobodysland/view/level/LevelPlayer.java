package com.mcoder.nobodysland.view.level;

import com.mcoder.jge.screen.Display;
import com.mcoder.nobodysland.Window;
import com.mcoder.nobodysland.gui.Inventory;
import com.mcoder.nobodysland.gui.InventoryItem;
import com.mcoder.nobodysland.gui.menu.GenericMenu;
import com.mcoder.nobodysland.gui.menu.LevelLostMenu;
import com.mcoder.nobodysland.gui.menu.LevelPassedMenu;
import com.mcoder.nobodysland.io.ResourceManager;
import com.mcoder.nobodysland.view.Game;
import com.mcoder.nobodysland.view.Item;
import com.mcoder.nobodysland.view.TextureConstants;
import com.mcoder.nobodysland.view.sprite.EntityType;
import com.mcoder.nobodysland.view.sprite.placeable.FixedGun;
import com.mcoder.nobodysland.view.sprite.placeable.Turret;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class LevelPlayer extends Display implements MouseListener {
	private final Level level;
	private final Inventory inventory;

	private final LinkedList<FixedGun> guns;

	private final int playerHp;
	private int playerHpLeft;
	private boolean done;

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

		addListener(this);
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
		//else Screen.getInstance().setTickSpeed(200);

		if (!done && level.isFinished()) {
			if (playerHpLeft > 0) {
				GenericMenu.invoke(new LevelPassedMenu());
				ResourceManager.saveData(Game.getUser(), level.getFileIndex() + 1);
			} else {
				GenericMenu.invoke(new LevelLostMenu());
				ResourceManager.saveData(Game.getUser(), 1);
			}
			done = true;
		}
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
		g2d.drawImage(TextureConstants.PLAYER_DAMAGE.getTexture().getImage(), 0, 0, Window.width, Window.height, null);
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

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Spot spot = level.findSpotUnder(e.getX(), e.getY());
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (spot.getFloor() == SpotContent.GRASS && spot.getGun() == null)
				Game.getPlayer().locateGun(spot);
		} else if (e.getButton() == MouseEvent.BUTTON3 && spot.getGun() != null)
			Game.getPlayer().removeGun(spot);
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {}
}
