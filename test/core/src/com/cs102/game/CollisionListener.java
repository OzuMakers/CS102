package com.cs102.game;

import java.util.Stack;

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
		        if (fa.getBody().getUserData() instanceof Player){
		        	((Player) fa.getBody().getUserData()).dispose();
		        }
		        else if (fb.getBody().getUserData() instanceof Player){
		        	((Player) fb.getBody().getUserData()).dispose();
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
