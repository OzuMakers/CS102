package com.cs102.game;

import java.util.ArrayList;
import java.util.Stack;

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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject{
	Body body;
	Animation animation;
	int ID;
	public String direction="Up";
	public Stack<Trace> contacts = new Stack<Trace>();
	
	public Stack<Trace> getStack(){
		return contacts;
	}
	
	Player(World currentworld, String texturelocation, float scale, Animation anim, float x, float y, int id){
		super(texturelocation, scale, x, y);
		ID=id;
		animation = anim;
		body = currentworld.createBody(this.GetBodyDef());
		body.setUserData(this);
		CircleShape cshape = new CircleShape();
		cshape.setRadius((this.GetSprite().getWidth() / 2) / scale);
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
	}
	
	void getSmaller(float howmuchw, float howmuchh, float scale){
		if (this.GetSprite().getWidth()<DFVR.diesizepx || this.GetSprite().getHeight()<DFVR.diesizepx) {
			if (this.ID==0) DFVR.gamestate=5; else DFVR.gamestate=4;}
		if (DFVR.gamestate==2){
		this.setSpriteSize(this.GetSprite().getWidth()-howmuchw, this.GetSprite().getHeight()-howmuchh);
		CircleShape shape = (CircleShape) body.getFixtureList().get(0).getShape(); 
		shape.setRadius((this.GetSprite().getWidth() / 2) / scale);}
	}
	
	Body getBody(){
		return body;
	}
	
	public void Draw(SpriteBatch batch, float deltat){
		batch.draw(animation.getKeyFrame(deltat,true),body.getPosition().x*100-sprite.getWidth() / 2,body.getPosition().y*100-sprite.getHeight() / 2, sprite.getWidth(),sprite.getHeight());
		/*	System.out.println("SpriteX"+sprite.getX() +"SpriteY:"+sprite.getY());
		System.out.println("BodyX"+body.getPosition().x +"BodyY:"+body.getPosition().y);
		System.out.println("MouseX: "+Gdx.input.getX()+ "MouseY: "+Gdx.input.getY()); */
		for(int i = 1; i<contacts.size(); i++){
			contacts.get(i).Draw(batch);
		}
	}
	
	/*public void Draw(SpriteBatch batch, float deltat, float x, float y){
		batch.draw(animation.getKeyFrame(deltat,true),sprite.getX()+x,sprite.getY()+y,sprite.getWidth(),sprite.getHeight());
		/*System.out.println("SpriteX"+sprite.getX() +"SpriteY:"+sprite.getY());
		System.out.println("BodyX"+body.getPosition().x +"BodyY:"+body.getPosition().y);
		System.out.println("MouseX: "+Gdx.input.getX()+ "MouseY: "+Gdx.input.getY());
		for(int i = 0; i<contacts.size(); i++){
			contacts.get(i).Draw(batch);
		}
	}*/
	
	public void setBodyLocation(Vector2 vec){
		body.setTransform(vec,body.getAngle());
	}
	
	public Vector2 getBodyVector2(){
		return body.getTransform().getPosition();
	}
	public void move(){
		
		if (Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
		float controlx=Gdx.input.getAzimuth();
		float controly=Gdx.input.getRoll();
		if (DFVR.isServer) {
			if ((controly>-180 && controly<-100) || (controly>90 && controly<180)) //YUKARI
				DFVR.player.getBody().setLinearVelocity(DFVR.player.getBody().getLinearVelocity().x, DFVR.player.getBody().getLinearVelocity().y+0.1f);
			else if ((controly>-80 && controly<0) || (controly>0 && controly<90)) //AÞAÐI
				DFVR.player.getBody().setLinearVelocity(DFVR.player.getBody().getLinearVelocity().x, DFVR.player.getBody().getLinearVelocity().y-0.1f);
			if ((controlx>-125 && controlx<45)) //saða
				 DFVR.player.getBody().setLinearVelocity(0.1f+DFVR.player.getBody().getLinearVelocity().x, DFVR.player.getBody().getLinearVelocity().y);
			else if (controlx>-145 && controly<45) //sol
				DFVR.player.getBody().setLinearVelocity(-0.1f+DFVR.player.getBody().getLinearVelocity().x, DFVR.player.getBody().getLinearVelocity().y);
		} else {
			if ((controly>-180 && controly<-100) || (controly>90 && controly<180)) //YUKARI
				 DFVR.opp.getBody().setLinearVelocity(DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y+0.1f);
			else if ((controly>-80 && controly<0) || (controly>0 && controly<90)) //AÞAÐI
				DFVR.opp.getBody().setLinearVelocity(DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y-0.1f);
			if ((controlx>-125 && controlx<45)) //saða
				 DFVR.opp.getBody().setLinearVelocity(0.1f+DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y);
			else if (controlx>-145 && controly<45) //sol
				DFVR.opp.getBody().setLinearVelocity(-0.1f+DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y);
		}
		
		} else {
			if (direction.equals("Up")) this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, this.getBody().getLinearVelocity().y+0.1f);
			else if (direction.equals("Down")) this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, this.getBody().getLinearVelocity().y-0.1f);
			else if (direction.equals("Right")) this.getBody().setLinearVelocity(0.1f+this.getBody().getLinearVelocity().x, this.getBody().getLinearVelocity().y);
			else if (direction.equals("Left")) this.getBody().setLinearVelocity(-0.1f+this.getBody().getLinearVelocity().x, this.getBody().getLinearVelocity().y);
		}
	
		if (this.getBody().getLinearVelocity().x>5f) this.getBody().setLinearVelocity(5f, this.getBody().getLinearVelocity().y);
		if (this.getBody().getLinearVelocity().y>5f) this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, 5f);
	}
	public void setMove(String s){
		direction=s;
	}
	
	void destroyBody(World currentworld)
    {
       currentworld.destroyBody(body);
       body = null; // that way you lose the only reference to the body as you should - it has been destroyed
    }
	
	void dropPoint(World currentworld, String texturelocation , float scale, float w, float h){
		Trace trace = new Trace(currentworld, texturelocation, scale, this.getBodyVector2().x,this.getBodyVector2().y, w*2/scale,h*2/scale,this);
		contacts.add(trace);
	}
	
	void increaseScore(float score){
		if (this.ID==0) DFVR.serverPoint+=score;
		else DFVR.clientPoint+=score;
	}

}
