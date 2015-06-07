package com.cs102.game;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Interrupt implements Runnable {
private boolean locked=false;
private static float delay = (float) 0.1; // seconds
	@Override
	public void run() {
		while(true){
			getAction();
	}	
	}
	
public void getAction(){
	if (!locked){
	DFVR.player.getSmaller((float)1, 1,100);
	DFVR.opp.getSmaller((float)1, 1,100);
		locked=true;
	Timer.schedule(new Task(){
	    @Override
	    public void run() {
	    locked=false;
	    }
	}, delay);
}
	
}
	
public void setDelay(float delay){
	this.delay=delay;
}
	}
	
