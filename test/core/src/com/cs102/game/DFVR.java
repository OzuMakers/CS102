package com.cs102.game;
/*
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DFVR extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
*/

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
import com.badlogic.gdx.Input;

public class DFVR extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	Body bodyEdgeScreen;
	//Box2DDebugRenderer debugRenderer;
	//Matrix4 debugMatrix;
	OrthographicCamera camera;
	BitmapFont font;
	float torque = 0.0f;
	boolean drawSprite = true;
	final float PIXELS_TO_METERS = 100f;
	int d3Effect = 0;
	float initazimuth = 0;
	float initroll = 0;
	
	//BACKGROUND CODE
	public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;
	//BACKGROUND CODE
	@Override
	public void create() {
		batch = new SpriteBatch();
	//	img = new Texture("initial_player.jpg");//
	//	sprite = new Sprite(img);//
	//	sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);//
		world = new World(new Vector2(0, -1f), true);
		
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.StaticBody;
		float w = Gdx.graphics.getWidth() / PIXELS_TO_METERS;
		float h = Gdx.graphics.getHeight() / PIXELS_TO_METERS - 50 / PIXELS_TO_METERS;
		bodyDef2.position.set(0, 0);
		FixtureDef fixtureDef2 = new FixtureDef();
		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(-w / 2, -h / 2, w / 2, -h / 2);
		fixtureDef2.shape = edgeShape;
		bodyEdgeScreen = world.createBody(bodyDef2);
		bodyEdgeScreen.createFixture(fixtureDef2);
		edgeShape.dispose();
		Gdx.input.setInputProcessor(this);
		//debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.
		getHeight());
		//camera.rotate(45);
		//background
		backgroundTexture = new Texture("background.png");
	    backgroundSprite =new Sprite(backgroundTexture);
	    backgroundSprite.setPosition(-backgroundSprite.getWidth()/2,-backgroundSprite.getHeight()/2);
	    //background
	    
	    //DO INITILIZE WHEN STARTED 
	    if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
	    initazimuth = Gdx.input.getAzimuth();
		initroll = Gdx.input.getRoll();
		}
	    	    
	}
	//BACKGROUND
	public void renderBackground(SpriteBatch a, float x, float y) {
		//backgroundSprite.setSize(x,y);
        backgroundSprite.draw(a);
    }
	
	//BACKGROUND
	private float elapsed = 0;
	@Override
	public void render() {
		if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
			float localx=((Gdx.input.getAzimuth()-initazimuth)/10);
			if (localx<-6) localx=-6;
			else if (localx>6) localx=6;
			float localy=((-(Gdx.input.getRoll()-initroll))/10);
			if (localy<-4.5) localy=-5;
			else if (localy>4.5) localy=5;
			body.setLinearVelocity(localx, localy);
			}
		
		camera.update();
		world.step(1f / 60f, 6, 2);
		body.applyTorque(torque, true);
		sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
		getWidth() / 2, (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight() / 2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
	//	debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
	//	PIXELS_TO_METERS, 0);
		
		Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
		
		batch.begin();
		renderBackground(batch,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2); //BACKGROUND
		
	/*	if (drawSprite) batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
		sprite.getOriginY(),
		sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
		getScaleY(), sprite.getRotation()); */
		
		/*font.draw(batch,
			"Restitution: " + body.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth() / 2,
		Gdx.graphics.getHeight() / 2);
		
		font.draw(batch,
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
			Gdx.graphics.getHeight() / 4-150);
		*/
		batch.end();
		////////////////////
		 Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
		batch.begin();
		renderBackground(batch,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//BACKGROUND
		
		/*if (drawSprite) batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
		sprite.getOriginY(),
		sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
		getScaleY(), sprite.getRotation()); */
	
		/*font.draw(batch,
			"Restitution: " + body.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth() / 2,
		Gdx.graphics.getHeight() / 2);
		
		font.draw(batch,
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
			Gdx.graphics.getHeight() / 4-150);

		*/
		batch.end();
		
		//debugRenderer.render(world, debugMatrix);
	}@Override
	public void dispose() {
		img.dispose();
		world.dispose();
	}@Override
	public boolean keyDown(int keycode) {
		return false;
	}@Override
	public boolean keyUp(int keycode) {
		
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
		if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1) drawSprite = !drawSprite;
		return false;
	}@Override
	public boolean keyTyped(char character) {
		return false;
	} // On touch we apply force from the direction of the users touch.// This could result in the object "spinning"
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		body.applyForce(1f, 1f, screenX, screenY, true); //body.applyTorque(0.4f,true);
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
