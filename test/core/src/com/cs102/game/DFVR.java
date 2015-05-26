/*background
		backgroundTexture = new Texture("background.png");
	    backgroundSprite.setPosition(-backgroundSprite.getWidth()/2,-backgroundSprite.getHeight()/2);
	    */
//		renderBackground(batch,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2); //BACKGROUND
		
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
		//Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
		//OTHER VIEWPORT: Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );

/*
		if (keycode == Input.Keys.RIGHT) body.setLinearVelocity(1f, 0f);
		if (keycode == Input.Keys.LEFT) body.setLinearVelocity(-1f, 0f);
		if (keycode == Input.Keys.UP) body.applyForceToCenter(0f, 10f, true);
		if (keycode == Input.Keys.DOWN) body.applyForceToCenter(0f, -10f, true);
		if (keycode == Input.Keys.RIGHT_BRACKET) torque += 0.1f;
		if (keycode == Input.Keys.LEFT_BRACKET) torque -= 0.1f;
		if (keycode == Input.Keys.BACKSLASH) torque = 0.0f;
		if (keycode == Input.Keys.SPACE || keycode == Input.Keys.NUM_2) {
			body.setLinearVelocity(0f, 0f);
			body.setAngularVelocity(0f);
			torque = 0f;
			sprite.setPosition(0f, 0f);
			body.setTransform(0f, 0f, 0f);
		}
		if (keycode == Input.Keys.COMMA) {
			body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution() - 0.1f);
		}
		if (keycode == Input.Keys.PERIOD) {
			body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution() + 0.1f);
		}
		if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1) drawSprite = !drawSprite;*/

   //DO INITILIZE WHEN STARTED 
	 /*   if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
	    initazimuth = Gdx.input.getAzimuth();
		initroll = Gdx.input.getRoll();
		} */

package com.cs102.game;

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

public class DFVR extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	World world;

	LineWall[] wallArray = new LineWall[4];
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
	
	
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -1f), true);
		Gdx.input.setInputProcessor(this);
		debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//StaticObject B.
		//Note: Texture is not used but required to create since super parent needs it.
		wallArray[0] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[1] = new LineWall(world, "background.png", 100 , Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[2] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		wallArray[3] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2-2, Gdx.graphics.getHeight()/2, -Gdx.graphics.getWidth()/2-2, -Gdx.graphics.getHeight()/2);
		//StaticObject E.
		
		//DynamicObject B.
		player = new Player(world, "badlogic.jpg", 100);
		player2 = new Player(world, "initial_player.jpg", 100, 100, 100);
		//DynamicObject E.
		
	}
	
	private float elapsed = 0;
	
	@Override
	public void render() {
		
		camera.update();
		world.step(1f / 60f, 6, 2);
		
		//UPDATE PLAYER HERE, i may want do it 60 times in second (same as world step) to avoid over doing it.
		player.Update(0, 100, 0, 0);
		player2.Update(0, 100, 0, 0);
		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
		
		batch.begin();
		player.Draw(batch);
		player2.Draw(batch);
		batch.end();
		
		debugRenderer.render(world, debugMatrix);
		
	}
	
	@Override
	public void dispose() {
		//Player dispose, other stuff dispose
		world.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}@Override
	public boolean keyUp(int keycode) {
		return false;
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

