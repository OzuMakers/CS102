package com.cs102.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Trace extends StaticObject{
	boolean disposed=false;
	Body bodyEdgeScreen;
	public Player owner;
	float initx;
	float inity;
	float scale;
	Trace(World currentworld, String texturelocation, float scale, float x, float y, float w, float h, Player owner){
		super(texturelocation, scale, x, y);
	        initx=x;
	        inity=y;
	        this.scale=scale;
	        FixtureDef fixtureDef2 = new FixtureDef();
	        PolygonShape shape = new PolygonShape();//
	    	shape.setAsBox(sprite.getWidth()/scale, sprite.getHeight()/scale);//
	        fixtureDef2.shape = shape;
	        fixtureDef2.isSensor = true;
	        bodyEdgeScreen = currentworld.createBody(this.GetBodyDef());
	        bodyEdgeScreen.createFixture(fixtureDef2);
	        bodyEdgeScreen.setTransform(x, y, 0);
	        shape.dispose();
	        this.owner = owner;
	        bodyEdgeScreen.setUserData(this);
	        float delay = (float) 3; // seconds

			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			    	disposed=true;
			    	
			    }
			}, delay);
	}
	
	public Body getBody(){
		return bodyEdgeScreen;
	}
	public void Draw(SpriteBatch batch){
		//sprite.draw(batch);
		batch.draw(sprite, initx*scale-sprite.getWidth()/2, inity*scale-sprite.getHeight()/2,
				sprite.getWidth(),
				sprite.getHeight());}
}
