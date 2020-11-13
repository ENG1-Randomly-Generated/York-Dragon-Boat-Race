package com.mygdx.dragonboatgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.dragonboatgame.DragonBoatGame;
import com.mygdx.dragonboatgame.screens.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1028;
		new LwjglApplication(new GameManager(), config);
	}
}
