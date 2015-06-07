/*Changelog:
 *[ADDED] RANDOM SPAWN BONUS
 *[ADDED] BOUNCING SOUND
 */
/*NEXT:
 * ARRANGING HUDS
 * ICE WINNING SOUND
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

import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
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
import com.cs102.screens.Splash;

public class DFVR extends Game implements InputProcessor {

	private TextureAtlas atlas;
	private Animation animation;
	private float timePassed = 0;
	public static boolean isServer=true;
	public boolean gamestarted=false;
	private boolean stopRendering=false;
	public final static float howmuchbiggerpx=50;
	SpriteBatch batch;
	public  static World world;
	
	public static Stack<Object2> WorldSensitiveActions = new Stack<Object2>();
	
	LineWall[] wallArray = new LineWall[4];
	Object2[] objectArray = new Object2[1];
	public static Player player;
	public static Player opp;
	public static MagicBolt magicbolt;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    static int gamestate=-1;
    public static int serverPoint=0;
    public static int clientPoint=0;
    public static final float playerW=100;
    public static final float playerH=100;
    /* Just Started: -1
     * Server: 0
     * Client: 1
     * Action: 2
     * Paused: 3
     * Server won: 4
     * Client won: 5
     * Tie: 6
     * GameOVER Server Won: 7
     * GameOVER Client Won: 8
     */
    /*
     * ServerPlayer ID: 0
     * ClientPlayer ID: 1
     */
    //Config B.
    static float goalsizepx=300;
  //Config E.
    
    boolean relocate=false;
    
	OrthographicCamera camera;
	BitmapFont font;
	float torque = 0.0f;
	
	final float PIXELS_TO_METERS = 100f;
	public static int WIDTH = 750;
	public static int HEIGHT = 500;
	float initazimuth = 0;
	float initroll = 0;
	boolean calibrated = false;
	int platform=0;
	static int control=0;
	static boolean dropped=false;
	static float scaledwidth = WIDTH/100;
	static float scaledheight = HEIGHT/100;
	/* 0: Desktop
	 * 1: Android
	*/
	
	@Override
	public void create() {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("Gamemusic.mp3"));
		long id = sound.play(1.0f);
		sound.setLooping(id, true);
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
		objectArray[0] = new Object2("background.png",0,0);
		
		//Object E.
		
		//StaticObject B.
		wallArray[0] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[1] = new LineWall(world, "background.png", 100 , Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		wallArray[2] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		wallArray[3] = new LineWall(world, "background.png", 100 , -Gdx.graphics.getWidth()/2-2, Gdx.graphics.getHeight()/2, -Gdx.graphics.getWidth()/2-2, -Gdx.graphics.getHeight()/2);
		//StaticObject E. - Note: Texture is not used but required to create since super parent needs it.
		
		//DynamicObject B.
		atlas = new TextureAtlas(Gdx.files.internal("fireballanim.atlas"));
		player = new Player(world, "initial_player.jpg", 100, new Animation(1/10f, atlas.getRegions()), 0,0,0);
		atlas = new TextureAtlas(Gdx.files.internal("iceballanim.atlas"));
		opp = new Player(world, "initial_player.jpg", 100, new Animation(1/10f, atlas.getRegions()), 100, 100, 1);
		atlas = new TextureAtlas(Gdx.files.internal("magicbolt.atlas"));
		magicbolt= new MagicBolt(world,"initial_player.jpg",100,new Animation(1/10f, atlas.getRegions()), 50,50);
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
		(new Thread(new Interrupt())).start();
	}
	
	private float elapsed = 0;
	
	private void initializePlayers(){
//		System.out.println(scaledwidth);
	//	System.out.println(scaledheight);
		float rand1 = new Random().nextFloat();
		rand1 = rand1*(scaledwidth/2);
		float rand2 = new Random().nextFloat();
		rand2 = rand2*(scaledheight/2);
		DFVR.player.setBodyLocation(new Vector2(rand1,rand2));
		 rand1 = new Random().nextFloat();
		rand1 = rand1*(scaledwidth/2);
		rand2 = new Random().nextFloat();
		rand2 = rand2*(scaledheight/2);
		DFVR.opp.setBodyLocation(new Vector2(-rand1,-rand2));
		
		while(!player.contacts.isEmpty()){
			world.destroyBody(((Trace)player.contacts.pop()).getBody());
		}
		
		while(!opp.contacts.isEmpty()){
			world.destroyBody(((Trace)opp.contacts.pop()).getBody());
		}
		
		player.setSpriteSize(DFVR.playerW,DFVR.playerH);
		opp.setSpriteSize(DFVR.playerW,DFVR.playerH);
	}
	private void updateMovement(){
		if (isServer){
			player.move();
			opp.move();
		}
		if (!isServer){
		 for(Vector2 vec2 : ClientProgram.transfer1) {
		        DFVR.player.setBodyLocation(vec2);
		        ClientProgram.transfer1.removeValue(vec2, true);
		    }
		 
		 for(Vector2 vec2 : ClientProgram.transfer2) {
		        DFVR.opp.setBodyLocation(vec2);
		        ClientProgram.transfer2.removeValue(vec2, true);
		    }
		}
		if (!dropped){
			dropped=true;
			player.dropPoint(world,"line_burn.png", 100, 50,50);
			opp.dropPoint(world,"line_ice.png", 100, 50,50);
			if (player.contacts.size()>50) {world.destroyBody(player.contacts.get(0).getBody()); player.contacts.remove(0);}
			if (opp.contacts.size()>50) {world.destroyBody(opp.contacts.get(0).getBody()); opp.contacts.remove(0);}
		float delay = (float) 0.03; // seconds

		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	dropped=false;
		    	
		    }
		}, delay);
		}
		
		if (calibrated) {
			player.Update(0, 100, initazimuth, initroll);
			opp.Update(0, 100, initazimuth, initroll);
			} else if (platform == 0) {
				player.Update(0, 100, 0, 0);
				opp.Update(0, 100, 0, 0);
				}
	}
	
	private void clearScreen(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private void removeBodies(World currentworld){
		while(!WorldSensitiveActions.isEmpty()){
			Object2 currentobject= WorldSensitiveActions.pop();
			if (currentobject instanceof MagicBolt)
			((MagicBolt)currentobject).reLocate();
		}
	}
	
	@Override
	public void render() {
		clearScreen();
		
		if (gamestate==0 && !gamestarted) {(new Thread(new ServerProgram())).start(); gamestarted=true; gamestate=2;}
		else if(gamestate==1 && !gamestarted) {(new Thread(new ClientProgram())).start(); gamestarted=true; gamestate=2;}
		if (gamestate==4){serverPoint+=1; initializePlayers(); gamestate=2; Sound sound = Gdx.audio.newSound(Gdx.files.internal("FireBall.mp3"));
    	sound.play(1.0f);}
		else if (gamestate==5){clientPoint+=1; initializePlayers(); gamestate=2;}
		else if (gamestate==6){clientPoint-=1; serverPoint-=1; initializePlayers(); gamestate=2;}
		if (gamestate==2) removeBodies(world);
		//Player Movements&Trace B.
		if(gamestate==2)updateMovement(); //If inAction
		//Player Movements&Trace E.
		//Update world
		if(gamestate==2)world.step(1f / 100f, 6, 2); //If inAction
		
		batch.setProjectionMatrix(camera.combined);
		
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,PIXELS_TO_METERS, 0);

			if (platform==1) Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			if(gamestate==2){//If inAction
				timePassed += Gdx.graphics.getDeltaTime();
			//	System.out.println(timePassed);
				renderAction(batch,timePassed);
			}
			renderHUDs();
			batch.end();
			
			if (platform==1) Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
			batch.begin();
			if(gamestate==2){ //If inAction
				timePassed += Gdx.graphics.getDeltaTime();
				renderAction(batch,timePassed);
			}
			renderHUDs();
			batch.end();	
		
//	debugRenderer.render(world, debugMatrix);
		
	}

	@Override
	public void dispose() {
		player.dispose();
		opp.dispose();
		world.dispose();
	}
	
	private void renderAction(SpriteBatch batch, float timePassed){
		objectArray[0].Draw(batch); //RENDER BACKGROUND
		objectArray[0].CenterSprite();//Center the background
		player.Draw(batch,timePassed); //RENDER SERVER PLAYER
		opp.Draw(batch,timePassed); //RENDER CLIENT PLAYER
		magicbolt.Draw(batch, timePassed);
	}
	
private void renderHUDs(){
	font.draw(batch,
			"Iceball: " + clientPoint + " Fireball: "+ serverPoint, -Gdx.graphics.getWidth() / 4-150,
			Gdx.graphics.getHeight() / 4+50);
if (gamestate==3)	font.draw(batch,
			"PAUSED", -Gdx.graphics.getWidth() / 4,
		Gdx.graphics.getHeight() / 4-50);
if (gamestate==-1)	font.draw(batch,
		"Server: Press 'V' Client: Press 'C'", -Gdx.graphics.getWidth() / 4-150,
		Gdx.graphics.getHeight() / 4+75);
	/*if (control==1) font.draw(batch,
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
*/
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
		
		if (keycode == Input.Keys.D) DFVR.opp.setMove("Right");	
		if (keycode == Input.Keys.A) DFVR.opp.setMove("Left");	
		if (keycode == Input.Keys.W) DFVR.opp.setMove("Up");	
		if (keycode == Input.Keys.S) DFVR.opp.setMove("Down");	
		
		if (keycode == Input.Keys.P) {if (gamestate>2) gamestate=2; else gamestate=3;}
		if (keycode == Input.Keys.V) {if (gamestate==-1) gamestate=0;}
		if (keycode == Input.Keys.C) {if (gamestate==-1) gamestate=1;}
		}
		else {
			if (keycode == Input.Keys.RIGHT) ClientProgram.SendRight();
		if (keycode == Input.Keys.LEFT)ClientProgram.SendLeft();
		if (keycode == Input.Keys.UP) ClientProgram.SendUp();
		if (keycode == Input.Keys.DOWN) ClientProgram.SendDown();
		if (keycode == Input.Keys.P) {if (gamestate>2) gamestate=2; else gamestate=3;}
		if (keycode == Input.Keys.V) {if (gamestate==-1) gamestate=0;}
		if (keycode == Input.Keys.C) {if (gamestate==-1) gamestate=1;}
		
		if (keycode == Input.Keys.D) DFVR.opp.setMove("Right");	
		if (keycode == Input.Keys.A) DFVR.opp.setMove("Left");	
		if (keycode == Input.Keys.W) DFVR.opp.setMove("Up");	
		if (keycode == Input.Keys.S) DFVR.opp.setMove("Down");	
		
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

