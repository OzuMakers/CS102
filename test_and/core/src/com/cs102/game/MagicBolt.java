package com.cs102.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MagicBolt extends StaticObject{
	Body body;
	Animation animation;
	MagicBolt(World currentworld, String dummy, float scale, Animation anim, float x, float y) {
		super(dummy, scale, x, y);
		animation = anim;
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
		CircleShape cshape = new CircleShape();
		sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
		cshape.setRadius((this.GetSprite().getWidth() / 2) / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = cshape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		cshape.dispose();
		addToSensitiveList();
	}
	
	Body getBody(){
		return body;
	}
	
	public void Draw(SpriteBatch batch, float deltat){
		batch.draw(animation.getKeyFrame(deltat,true),body.getPosition().x*100-sprite.getWidth() / 2,body.getPosition().y*100-sprite.getHeight() / 2, sprite.getWidth(),sprite.getHeight());
	}
	
	public void makeBigger(Player player, float howmuchw, float howmuchh, float scale){
			if (this.GetSprite().getWidth()>DFVR.goalsizepx || this.GetSprite().getHeight()>DFVR.goalsizepx) {
				if (player.ID==0) DFVR.gamestate=4; else DFVR.gamestate=5;
				System.out.println("GameOVER, Size GOAL Reached");
				}
			if (DFVR.gamestate==2){
			player.setSpriteSize(this.GetSprite().getWidth()+howmuchw, this.GetSprite().getHeight()+howmuchh);
			CircleShape shape = (CircleShape) body.getFixtureList().get(0).getShape(); 
			shape.setRadius((this.GetSprite().getWidth() / 2) / scale);}
	}
	
	public void reLocate(){
		float sign = new Random().nextFloat();
		if (sign>0.5){
		float rand1 = new Random().nextFloat();
		rand1 = rand1*(DFVR.scaledwidth/2);
		float rand2 = new Random().nextFloat();
		rand2 = rand2*(DFVR.scaledheight/2);
		this.setBodyLocation(new Vector2(rand1,rand2));
	} else {
		float rand1 = new Random().nextFloat();
		rand1 = rand1*(DFVR.scaledwidth/2);
		float rand2 = new Random().nextFloat();
		rand2 = rand2*(DFVR.scaledheight/2);
		this.setBodyLocation(new Vector2(-rand1,-rand2));
	}
		}
	
	public void setBodyLocation(Vector2 vec){
		body.setTransform(vec,body.getAngle());
	}
	
	public void addToSensitiveList(){
		DFVR.WorldSensitiveActions.add(this);
	}

}
