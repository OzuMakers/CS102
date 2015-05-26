package com.cs102.game;

import com.badlogic.gdx.physics.box2d.BodyDef;

abstract public class StaticObject extends Object{
	BodyDef bodyDef = new BodyDef();
	
	StaticObject(String str, float scale){
		super(str);
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set((this.GetSprite().getX() + this.GetSprite().getWidth() / 2) / scale, (this.GetSprite().getY() + this.GetSprite().getHeight() / 2) / scale);
		
	}
	
	StaticObject(String str, float scale, float x, float y){
		super(str, x, y);
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 0);
		
	}
	
	public BodyDef GetBodyDef(){
		return bodyDef;
	}
	
}