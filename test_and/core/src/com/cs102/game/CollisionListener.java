package com.cs102.game;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionListener implements ContactListener{
	private Stack<Contact> contacts = new Stack<Contact>();
	 
	 
	public void checkCollision(){
		    if(contacts.isEmpty() ) return;
		    while(!contacts.isEmpty()){
		        Contact contact = contacts.pop();
		        Fixture fa = contact.getFixtureA();
		        Fixture fb = contact.getFixtureB();

		        if(fa == null || fb == null){ return ;}
		        if (fa.getBody().getUserData() instanceof Player && (fb.getBody().getUserData() instanceof Player)&&  DFVR.gamestate==2){
		        	Sound sound = Gdx.audio.newSound(Gdx.files.internal("Crash.wav"));
		        	sound.play(1.0f);
		        	DFVR.gamestate=6;
		        	System.out.println("Tie!");
		        	
		        }
		       if (fa.getBody().getUserData() instanceof Trace && (fb.getBody().getUserData() instanceof Player)){
		        	if (((Trace)fa.getBody().getUserData()).owner != fb.getBody().getUserData()&&  DFVR.gamestate==2) {
		        	if (((Player) fb.getBody().getUserData()).ID==0){ DFVR.gamestate=5;
		        	System.out.println("ICEBALL WON");}
		        	else { DFVR.gamestate=4;
		        	System.out.println("Server WON");}
		        	} }
		        else if (fb.getBody().getUserData() instanceof Trace && (fa.getBody().getUserData() instanceof Player)&&  DFVR.gamestate==2){
		        	if (((Trace)fb.getBody().getUserData()).owner != fa.getBody().getUserData()) {
		        		if (((Player) fb.getBody().getUserData()).ID==0){ DFVR.gamestate=5;
			        	System.out.println("Client WON");
			        	DFVR.clientPoint+=1;} else
		        		{ DFVR.gamestate=4;
			        	System.out.println("Server WON");
			        	DFVR.serverPoint+=1;}
		        	} }
		        else  if ((fa.getBody().getUserData() instanceof MagicBolt) && (fb.getBody().getUserData() instanceof Player)){
		        	((MagicBolt)fb.getBody().getUserData()).addToSensitiveList();
		        	((MagicBolt)fb.getBody().getUserData()).makeBigger(((Player)fa.getBody().getUserData()), DFVR.howmuchbiggerpx, DFVR.howmuchbiggerpx, 100);
		        } 
		        else  if ((fb.getBody().getUserData() instanceof MagicBolt) && (fa.getBody().getUserData() instanceof Player)){
		        	((MagicBolt)fb.getBody().getUserData()).addToSensitiveList();
		        	((MagicBolt)fb.getBody().getUserData()).makeBigger(((Player)fa.getBody().getUserData()), DFVR.howmuchbiggerpx, DFVR.howmuchbiggerpx, 100);
		        	}
		        else  if ((fb.getBody().getUserData() instanceof LineWall) || (fa.getBody().getUserData() instanceof LineWall)){
		        	Sound sound = Gdx.audio.newSound(Gdx.files.internal("ball_bounce.mp3"));
		        	sound.play(2.0f);
		        	}
		    }
		}
   
	 @Override
    public void beginContact(Contact contact) {
    contacts.push(contact);
    checkCollision();
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		DFVR.control=0;
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
