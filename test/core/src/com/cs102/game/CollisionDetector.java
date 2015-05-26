package com.cs102.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionDetector implements ContactListener{
	CollisionDetector
	int contacts = world.getContactCount();

	public void checkCollision(){
	    if(contacts.isEmpty() ) return;
	    while(!contacts.isEmpty()){
	        Contact contact = contacts.pop();
	        Fixture fa = contact.getFixtureA();
	        Fixture fb = contact.getFixtureB();

	        if(fa == null || fb == null){ return ;}

	        boolean collideOnce  = false;
	        if(fa.getBody().getType() == BodyType.DynamicBody){
	            collideOnce = fa.getUserData()==null? true : false;
	            fa.setUserData("t");
	        }else if(fb.getBody().getType() == BodyType.DynamicBody){
	            collideOnce = fb.getUserData()==null? true: false;
	            fb.setUserData("t");
	        }

	        if((fa.getBody().getType() == BodyType.DynamicBody && fb.getBody().getType() == BodyType.StaticBody) || (fb.getBody().getType() == BodyType.DynamicBody && fa.getBody().getType() == BodyType.StaticBody)){
	            if( collideOnce ){
	                SoundManager.play(SoundType.DROP , 0.5f);
	            }
	        }
	    }
	}

	// 
	    private Stack<Contact> contacts = new Stack<Contact>();
	    @Override
	public void beginContact(Contact contact) {
	    // TODO Auto-generated method stub
	    contacts.push(contact);
	}

	@Override
	public void endContact(Contact contact) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	    // TODO Auto-generated method stub

	}
	
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
