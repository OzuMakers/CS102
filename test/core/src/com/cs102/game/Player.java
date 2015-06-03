package com.cs102.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject{
	Body body;
	Animation animation;
	int ID;
	
	Player(World currentworld, String texturelocation, float scale, Animation anim, int id){
		super(texturelocation, scale);
		ID=id;
		animation = anim;
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
		CircleShape cshape = new CircleShape();
		cshape.setRadius((this.GetSprite().getWidth() / 2-15) / scale);
		//PolygonShape shape = new PolygonShape();
		//shape.setAsBox(this.GetSprite().getWidth() / 2 / scale, this.GetSprite().getHeight() / 2 / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = cshape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		cshape.dispose();
	}
	
	Player(World currentworld, String texturelocation, float scale, Animation anim, float x, float y, int id){
		super(texturelocation, scale, x, y);
		ID=id;
		animation = anim;
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
	//	PolygonShape shape = new PolygonShape();
		//shape.setAsBox(this.GetSprite().getWidth() / 2 / scale, this.GetSprite().getHeight() / 2 / scale);
		CircleShape cshape = new CircleShape();
		cshape.setRadius((this.GetSprite().getWidth() / 2-15) / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = cshape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		cshape.dispose();
	}

	@Override
	void Update(float torque ,float scale, float initazimuth, float initroll) {
			body.applyTorque(torque, true);
			this.GetSprite().setPosition((body.getPosition().x * scale) - this.GetSprite().
			getWidth() / 2, (body.getPosition().y * scale) - this.GetSprite().getHeight() / 2);
			this.GetSprite().setRotation((float) Math.toDegrees(body.getAngle()));
			
			/*if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
				float controlx=Gdx.input.getAzimuth();
				float controly=Gdx.input.getRoll();
				
				float localx=(controlx-initazimuth)/5;
				
				float localy=(initroll-controly)/5;
				
				if (controlx>0 && controlx<180 && controly>-180 && controly<0)
				body.setLinearVelocity(localx, localy);
				else if (controly>-180 && controly<0)
					body.setLinearVelocity(body.getLinearVelocity().x, localy);
				else if (controlx>0 && controlx<180)
					body.setLinearVelocity(localx,body.getLinearVelocity().y);
				}
			else {
				float x = this.getBody().getLinearVelocity().x;
				float y = this.getBody().getLinearVelocity().y;
				if (x>6) x=6;
				else if (x<-6) x=-6;
				if (y>4.5) y=(float) 4.5; 
				if (y<-4.5) y=(float) -4.5; 
				this.getBody().setLinearVelocity(x, y);
			}*/
	}
	
	Body getBody(){
		return body;
	}
	
	public void Draw(SpriteBatch batch, float deltat){
		batch.draw(animation.getKeyFrame(deltat,true),sprite.getX(),sprite.getY());
	}
	
	public void Draw(SpriteBatch batch, float deltat, float x, float y){
		batch.draw(animation.getKeyFrame(deltat,true),sprite.getX()+x,sprite.getY()+y);
	}
	
	public void setBodyLocation(float x, float y){
		body.setTransform(x,y,body.getAngle());
	}
	
	public Vector2 getBodyVector2(){
		return body.getTransform().getPosition();
	}

}
