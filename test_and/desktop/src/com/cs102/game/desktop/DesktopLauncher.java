package com.cs102.game.desktop;

import trial.GameProject;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cs102.game.DFVR;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=GameProject.WIDTH; // sets window width
        config.height=GameProject.HEIGHT;  // sets window height
		new LwjglApplication(new DFVR(), config);
	}
}
