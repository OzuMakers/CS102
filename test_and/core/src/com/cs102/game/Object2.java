package com.cs102.game;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Object2 {
	Sprite sprite;
	Texture img;
	 
	Object2(String spr, float x, float y){
		img = new Texture(spr);
		sprite = new Sprite(img);
		sprite.setPosition(x, y);
	}
	 
	 void SetImage(String spr){
		img = new Texture(spr);
		sprite = new Sprite(img);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
		 }
	 
	 void CenterSprite(){
			sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
			 }
	 
	 void SetImage(String spr, float x, float y){
		img = new Texture(spr);
		sprite = new Sprite(img);
		sprite.setPosition(x, y);
	 }
	 
	 Sprite GetSprite(){
		 return sprite;
	 }
	 
	 public Texture GetTexture(){
		 return img;
	 }
	 
	 public void setSpriteSize(float w, float h){
		 sprite.setSize(w,h);
		 
	 }
		
	public void Draw(SpriteBatch batch){
		sprite.draw(batch);
		/*	batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
					sprite.getOriginY(),
					sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
					getScaleY(), sprite.getRotation());		*/
		}
	
	public void dispose(){
		img.dispose();
	}
}
