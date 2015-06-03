package com.cs102.game;

import java.util.HashMap;
import java.util.Map;

//imports about libgdx engine

import com.esotericsoftware.kryonet.Listener;


public class ClientProgram extends Listener implements Runnable{
	static Network network = new Network();
	
	/*public static void main(String[] args)  throws Exception {

		network.connect();
		
	
			update();
	
		System.exit(0);
	}*/
	
	public static void update(){
			NetPack pd = new NetPack();
			pd.OppPlayerX = DFVR.player.getBodyVector2().x;
			pd.OppPlayerX = DFVR.player.getBodyVector2().y;	
			System.out.println("Network Update Sent");
			network.client.sendTCP(pd);
			
//			player.networkPosition.y = player.position.y;
		}

	@Override
	public void run() {
		network.connect();
		
		while(true){
		update();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
}
