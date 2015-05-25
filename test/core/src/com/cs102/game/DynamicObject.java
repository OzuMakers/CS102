package com.cs102.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class DynamicObject extends Object{
	BodyDef bodyDef = new BodyDef();
	
	DynamicObject(String str, float scale){
		super(str);
		bodyDef.type = BodyDef.BodyType.DynamicBody;//
		bodyDef.position.set((this.GetSprite().getX() + this.GetSprite().getWidth() / 2) / scale, (this.GetSprite().getY() + this.GetSprite().getHeight() / 2) / scale);//
		
	}
	
	DynamicObject(String str, float scale, float x, float y){
		super(str, x, y);
		bodyDef.type = BodyDef.BodyType.DynamicBody;//
		bodyDef.position.set((this.GetSprite().getX() + this.GetSprite().getWidth() / 2) / scale, (this.GetSprite().getY() + this.GetSprite().getHeight() / 2) / scale);//
		
	}
	
	public BodyDef GetBodyDef(){
		return bodyDef;
	}
	
}
