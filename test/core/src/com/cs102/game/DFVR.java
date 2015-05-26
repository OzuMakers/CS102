/*Changelog:
 * 	Changelog Added
 * 	Platform Specific Code Added
 * 	Needs to be optimized by eliminating two batch on desktop
 * 	Changed initialization time from 0.3 to 0.5
 * 	Background added
 * 	Object creation by default creates at center additional x and y adjusted to libgdx coordinate system (middle point is (0,0))
 * 	Desktop controls added
 */

		
			/*font.draw(batch,
				"Restitution: " + body.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2); */
			
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
	

package com.cs102.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class DFVR extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	World world;

	LineWall[] wallArray = new LineWall[4];
	Object[] objectArray = new Object[1];
	Player player;
	Player player2;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    
	OrthographicCamera camera;
	BitmapFont font;
	float torque = 0.0f;
	
	final float PIXELS_TO_METERS = 100f;
	
	float initazimuth = 0;
	float initroll = 0;
	boolean calibrated = false;
	int platform=0;
	/* 0: Desktop
	 * 1: Android
	*/
	
	@Override
	public void create() {
		if(Gdx.app.getType() == ApplicationType.Android) {
		    platform=1;
		}
		else if(Gdx.app.getType() == ApplicationType.Desktop) {
		    platform=0;
		}
		
		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);
		Gdx.input.setInputProcessor(this);
		debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Object B.
		objectArray[0] = new Object("background.png");
//			renderBackground(batch,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2); //BACKGROUND
		//Object E.
		
		//StaticObject B.
		wallArray[0] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[1] = new LineWall(world, "background.png", 100 , Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[2] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		wallArray[3] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2-2, Gdx.graphics.getHeight()/2, -Gdx.graphics.getWidth()/2-2, -Gdx.graphics.getHeight()/2);
		//StaticObject E. - Note: Texture is not used but required to create since super parent needs it.
		
		//DynamicObject B.
		player = new Player(world, "badlogic.jpg", 100);
		player2 = new Player(world, "initial_player.jpg", 100, 100, 100);
		//DynamicObject E.
		
		//Initialize sensor values B.
		float delay = (float) 0.5; // seconds

		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
		    	    initazimuth = Gdx.input.getAzimuth();
		    		initroll = Gdx.input.getRoll();
		    		calibrated = true;
		    		}
		    }
		}, delay);
		//Initialize sensor values E.
		
	}
	
	private float elapsed = 0;
	
	@Override
	public void render() {
		
		camera.update();
		world.step(1f / 60f, 6, 2);
		
		//UPDATE PLAYER HERE, i may want do it 60 times in second (same as world step) to avoid over doing it.
		if (calibrated) {
		player.Update(0, 100, initazimuth, initroll);
		player2.Update(0, 100, initazimuth, initroll);
		} else if (platform == 0) {
			player.Update(0, 100, 0, 0);
			player2.Update(0, 100, 0, 0);
			}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);

			if (platform==1) Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			objectArray[0].Draw(batch);
			player.Draw(batch);
			player2.Draw(batch);
			font.draw(batch,
					"Platform: "+platform, -Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
			batch.end();
			
			if (platform==1) Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			objectArray[0].Draw(batch);
			player.Draw(batch);
			player2.Draw(batch);
			font.draw(batch,
					"Platform: "+platform, -Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
			batch.end();	
		
		debugRenderer.render(world, debugMatrix);
		
	}

	@Override
	public void dispose() {
		player.dispose();
		player2.dispose();
		world.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.RIGHT) player.getBody().setLinearVelocity(1f+player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y);
		if (keycode == Input.Keys.LEFT) player.getBody().setLinearVelocity(-1f+player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y);
		if (keycode == Input.Keys.UP) player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y+1f);
		if (keycode == Input.Keys.DOWN) player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y-1f);
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

