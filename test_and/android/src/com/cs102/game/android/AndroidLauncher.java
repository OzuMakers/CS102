package com.cs102.game.android;

/*
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.cs102.game.DFVR;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DFVR(), config);
	}
}
*/

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.cs102.game.DFVR;

public class AndroidLauncher extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initialize(new DFVR(), cfg);
    }  

}

/*font.draw(batch,
"X: " + (Gdx.input.getAccelerometerX() -9.8), -Gdx.graphics.getWidth() / 4,
Gdx.graphics.getHeight() / 4);
font.draw(batch,
"Y: " + Gdx.input.getAccelerometerY(), -Gdx.graphics.getWidth() / 4+50,
Gdx.graphics.getHeight() / 4+50);
font.draw(batch,
"Z: " + Gdx.input.getAccelerometerZ(), -Gdx.graphics.getWidth() / 4+100,
Gdx.graphics.getHeight() / 4+100);

font.draw(batch,
"AZIMUTH: " + Gdx.input.getAzimuth(), -Gdx.graphics.getWidth() / 4,
Gdx.graphics.getHeight() / 4-50);
font.draw(batch,
"Pitch: " + Gdx.input.getPitch(), -Gdx.graphics.getWidth() / 4-100,
Gdx.graphics.getHeight() / 4-100);
font.draw(batch,
"Roll: " + Gdx.input.getRoll(), -Gdx.graphics.getWidth() / 4-150,
Gdx.graphics.getHeight() / 4-150);*/