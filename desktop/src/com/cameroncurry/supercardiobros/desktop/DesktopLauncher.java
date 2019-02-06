package com.cameroncurry.supercardiobros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cameroncurry.supercardiobros.SuperCardioBros;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SuperCardioBros(), config);
		config.width = 1200;
		config.height = 624;


	}
}
