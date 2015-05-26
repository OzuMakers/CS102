package com.cs102.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject{
	Body body;
	
	Player(World currentworld, String texturelocation, float scale){
		super(texturelocation, scale);
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.GetSprite().getWidth() / 2 / scale, this.GetSprite().getHeight() / 2 / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	Player(World currentworld, String texturelocation, float scale, float x, float y){
		super(texturelocation, scale, x, y);
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.GetSprite().getWidth() / 2 / scale, this.GetSprite().getHeight() / 2 / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		shape.dispose();
	}

	@Override
	void Update(float torque ,float scale, float initazimuth, float initroll) {
			body.applyTorque(torque, true);
			this.GetSprite().setPosition((body.getPosition().x * scale) - this.GetSprite().
			getWidth() / 2, (body.getPosition().y * scale) - this.GetSprite().getHeight() / 2);
			this.GetSprite().setRotation((float) Math.toDegrees(body.getAngle()));
			
			if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
				float localx=((Gdx.input.getAzimuth()-initazimuth)/10);
				if (localx<-6) localx=-6;
				else if (localx>6) localx=6;
				float localy=((-(Gdx.input.getRoll()-initroll))/10);
				if (localy<-4.5) localy=(float) -4.5;
				else if (localy>4.5) localy=(float) 4.5;
				body.setLinearVelocity(localx, localy);
				}
			else {
				float x = this.getBody().getLinearVelocity().x;
				float y = this.getBody().getLinearVelocity().y;
				if (x>6) x=6;
				else if (x<-6) x=-6;
				if (y>4.5) y=(float) 4.5; 
				if (y<-4.5) y=(float) -4.5; 
				this.getBody().setLinearVelocity(x, y);
			}
	}
	
	Body getBody(){
		return body;
	}

}
