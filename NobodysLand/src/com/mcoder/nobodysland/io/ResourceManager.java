package com.mcoder.nobodysland.io;

import com.mcoder.nobodysland.view.level.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class ResourceManager {
	private static final String resPath = "res/";
	private static final String texturePath = resPath + "textures/";
	private static final String levelPath = resPath + "levels/";
	private static final String savesPath = resPath + "saves/";

	private static int lastSavedLevel;
	private static final String modelDataDelim = "=";
	private static final int animationDataSize = 3;

	static {
		lastSavedLevel = findLastLevel();
	}

	public static BufferedImage loadTexture(String name) {
		BufferedImage image = null;
		String path = texturePath + name + ".png";
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("[ERROR] Unable to load the texture: " + name);
			System.exit(-1);
		}

		return image;
	}

	public static HashMap<String, Double> loadModel(String name) {
		HashMap<String, Double> data = new HashMap<>();
		String path = texturePath + name + ".txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] info = line.trim().split(modelDataDelim);
				data.put(info[0], Double.valueOf(info[1]));
			}
		} catch (IOException e) {
			System.err.println("[ERROR] Unable to load the model " + name);
		}

		return data;
	}

	public static Level loadLevel(int num) {
		String relPath = "level" + num + ".dat";
		String fullPath = levelPath + relPath;
		Level level = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
			level = (Level) ois.readObject();
			level.setFileIndex(num);
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("[ERROR] Unable to load the level " + relPath);
		}

		return level;
	}

	public static int findLastLevel() {
		File dir = new File(levelPath);
		String[] levels = dir.list();
		if (levels != null && levels.length > 0 && loadLevel(levels.length) != null)
			return levels.length;
		return 0;
	}

	public static void saveLevel(Level level) {
		level.setFileIndex(lastSavedLevel++);
		String relPath = "level" + lastSavedLevel + ".dat";
		String fullPath = levelPath + relPath;
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
			oos.writeObject(level);
		} catch (IOException e) {
			System.err.println("[ERROR] Unable to save the level " + relPath);
		}
	}

	public static int loadLastSave(String player) {
		String relPath = player + ".dat";
		String fullPath = savesPath + relPath;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
			return (int) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("[ERROR] Unable to load player progress!");
		}

		return -1;
	}

	public static void saveData(String player, int nextLevel) {
		String relPath = player + ".dat";
		String fullPath = savesPath + relPath;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
			oos.writeObject(nextLevel);
		} catch(IOException e) {
			System.err.println("[ERROR] Unable to save player progress!");
		}
	}

	public static void removeSave(String player) {
		String relPath = player + ".dat";
		String fullPath = savesPath + relPath;
		File file = new File(fullPath);
		if (file.exists()) {
			try {
				if (!file.delete())
					throw new SecurityException();
			} catch(SecurityException e) {
				System.err.println("[ERROR] Unable to delete game progress!");
			}
		}
	}

	public static int[] loadAnimation(String name) {
		String relPath = name + ".txt";
		String fullPath = texturePath + relPath;
		int[] data = new int[animationDataSize];
		try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
			String line;
			int k = 0;
			while ((line = reader.readLine()) != null)
				data[k++] = Integer.parseInt(line);
		} catch (IOException e) {
			System.err.println("[ERROR] Unable to load animation " + relPath);
		}

		return data;
	}
}
