package com.cs102.game;

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
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.GetSprite().getWidth() / 2 / scale, this.GetSprite().getHeight() / 2 / scale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);
		shape.dispose();
	}

}
