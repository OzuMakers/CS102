package com.cs102.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

abstract public class StaticObject extends Object2{
	BodyDef bodyDef = new BodyDef();
	
	StaticObject(String str, float scale, float x, float y){
		super(str, x, y);
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
	}
	
	public BodyDef GetBodyDef(){
		return bodyDef;
	}
	
}