package com.cs102.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LineWall extends StaticObject{
	Body bodyEdgeScreen;
	
	LineWall(World currentworld, String texturelocation, float scale, float x1, float y1, float x2, float y2){
		super(texturelocation, scale,x1,x1);//DUMMY POSITION
	        
	        FixtureDef fixtureDef2 = new FixtureDef();
	        EdgeShape edgeShape = new EdgeShape();
	        edgeShape.set(x1/scale, y1/scale, x2/scale, y2/scale);
	        fixtureDef2.shape = edgeShape;
	        
	        bodyEdgeScreen = currentworld.createBody(this.GetBodyDef());
	        bodyEdgeScreen.createFixture(fixtureDef2);
	        bodyEdgeScreen.setUserData(this);
	        edgeShape.dispose();
	}
}
