package com.cs102.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class DynamicObject extends Object2{
	BodyDef bodyDef = new BodyDef();
	
	DynamicObject(String str, float scale, float x, float y){
		super(str, x, y);
		bodyDef.type = BodyDef.BodyType.DynamicBody;//
		//bodyDef.position.set(x, y);//
		
	}
	
	public BodyDef GetBodyDef(){
		return bodyDef;
	}
	
	abstract void Update(float torque ,float scale, float initazimuth, float initroll);
}
