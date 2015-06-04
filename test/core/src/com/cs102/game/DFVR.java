/*Changelog:
 * 	[FIXED] VR sensor values calibrated.
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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.cs102.game.ServerProgram;

public class DFVR extends ApplicationAdapter implements InputProcessor {

	private TextureAtlas atlas;
	private Animation animation;
	private float timePassed = 0;
	public static boolean isServer=true;
	public boolean gamestarted=false;
	
	SpriteBatch batch;
	public  static World world;

	LineWall[] wallArray = new LineWall[4];
	Object2[] objectArray = new Object2[1];
	public static Player player;
	public static Player opp;
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
	static int control=0;
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
		//World Config B.
				world = new World(new Vector2(0, 0), true);
				CollisionListener collisionlistener = new CollisionListener();
				world.setContactListener(collisionlistener);
		//World Config E.
		Gdx.input.setInputProcessor(this);
		debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Object B.
		objectArray[0] = new Object2("background.png");
		//Object E.
		
		//StaticObject B.
		wallArray[0] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[1] = new LineWall(world, "background.png", 100 , Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[2] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		wallArray[3] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2-2, Gdx.graphics.getHeight()/2, -Gdx.graphics.getWidth()/2-2, -Gdx.graphics.getHeight()/2);
		//StaticObject E. - Note: Texture is not used but required to create since super parent needs it.
		
		//DynamicObject B.
		atlas = new TextureAtlas(Gdx.files.internal("fireballanim.atlas"));
		player = new Player(world, "initial_player.jpg", 100, new Animation(1/10f, atlas.getRegions()), 0);
		atlas = new TextureAtlas(Gdx.files.internal("iceballanim.atlas"));
		opp = new Player(world, "initial_player.jpg", 100, new Animation(1/10f, atlas.getRegions()), 100, 100, 1);
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
		
	}
	
	private float elapsed = 0;
	
	@Override
	public void render() {
		gamestarted=true;
		
		camera.update();
		if (isServer){
			player.move();
			opp.move();
		}
		if (!isServer){
		 for(Vector2 vec2 : ClientProgram.transfer1) {
				System.out.println("Transformed to ("+vec2.x+","+vec2.y+")");
		        DFVR.player.setBodyLocation(vec2);
		        ClientProgram.transfer1.removeValue(vec2, true);
		    }
		 
		 for(Vector2 vec2 : ClientProgram.transfer2) {
			 	System.out.println("Transformed to ("+vec2.x+","+vec2.y+")");
		        DFVR.opp.setBodyLocation(vec2);
		        ClientProgram.transfer2.removeValue(vec2, true);
		    }
		}
		
		if (isServer && !gamestarted) (new Thread(new ServerProgram())).start();
		else if(!gamestarted) (new Thread(new ClientProgram())).start();
		world.step(1f / 100f, 6, 2);
		//UPDATE PLAYER HERE, i may want do it 60 times in second (same as world step) to avoid over doing it.
		if (calibrated) {
		player.Update(0, 100, initazimuth, initroll);
		opp.Update(0, 100, initazimuth, initroll);
		} else if (platform == 0) {
			player.Update(0, 100, 0, 0);
			opp.Update(0, 100, 0, 0);
			}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,PIXELS_TO_METERS, 0);

			if (platform==1) Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			objectArray[0].Draw(batch);
			timePassed += Gdx.graphics.getDeltaTime();//YAY HERE
			player.Draw(batch,timePassed);
			opp.Draw(batch,timePassed, 10f, 10f);
			if (control==1) font.draw(batch,
					"Platform: "+platform, -Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
			
			font.draw(batch,
					"AZIMUTH: " + (Gdx.input.getAzimuth()), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4-50);
			font.draw(batch,
					"Roll: " + (Gdx.input.getRoll()), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4-150);
			
			font.draw(batch,
					"AZIMUTH: " + (initazimuth), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4);
			font.draw(batch,
					"Roll: " + (initroll), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4+50);
			
			font.draw(batch,
					"AZIMUTH INITED: " + (Gdx.input.getAzimuth()-initazimuth), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4 +100);
			font.draw(batch,
					"Roll INITED: " + (-(Gdx.input.getRoll()-initroll)), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4+125);
			
			batch.end();
			
			if (platform==1) Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			objectArray[0].Draw(batch);
			timePassed += Gdx.graphics.getDeltaTime();//YAY HERE
			//+5 X AND +5 Y FOR 3D EFFECT
			player.Draw(batch,timePassed, 5f, 5f);
			opp.Draw(batch,timePassed, 15f, 15f);
			if (control==1) font.draw(batch,
					"Platform: "+platform, -Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
			
			font.draw(batch,
					"AZIMUTH: " + (Gdx.input.getAzimuth()), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4-50);
			font.draw(batch,
					"Roll: " + (Gdx.input.getRoll()), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4-150);
			
			font.draw(batch,
					"AZIMUTH: " + (initazimuth), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4);
			font.draw(batch,
					"Roll: " + (initroll), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4+50);
			
			font.draw(batch,
					"AZIMUTH INITED: " + (Gdx.input.getAzimuth()-initazimuth), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4 +100);
			font.draw(batch,
					"Roll INITED: " + (-(Gdx.input.getRoll()-initroll)), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4+125);
			
			
			
			// -45 - +45 roll aralýðý , roll orta kýsmý 0 
			// 0 ila -180 aralýðý ROLL(REAL)
			// 0 ÝLA 180 AZIMUTH (REAL)
//			azimut -100 +50 aralýðý
//			
			
			batch.end();	
		
	//	debugRenderer.render(world, debugMatrix);
		
	}

	@Override
	public void dispose() {
		player.dispose();
		opp.dispose();
		world.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}@Override
	public boolean keyUp(int keycode) {
		if (isServer){
		if (keycode == Input.Keys.RIGHT) ServerProgram.SendRight();
		if (keycode == Input.Keys.LEFT) ServerProgram.SendLeft();
		if (keycode == Input.Keys.UP) ServerProgram.SendUp();
		if (keycode == Input.Keys.DOWN) ServerProgram.SendDown();
		}
		else {
			if (keycode == Input.Keys.RIGHT) ClientProgram.SendRight();
		if (keycode == Input.Keys.LEFT)ClientProgram.SendLeft();
		if (keycode == Input.Keys.UP) ClientProgram.SendUp();
		if (keycode == Input.Keys.DOWN) ClientProgram.SendDown();
		} 
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

