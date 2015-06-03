package com.cs102.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cs102.networkpacks.*;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class ClientProgram extends Listener implements Runnable{
	public static boolean gameover=false;
	
	static Client client;
	
	static String ip = "localhost";
	
	static int tcpPort = 27960, udpPort = 27960;

	@Override
	public void run() {
		System.out.println("Connecting to the server...");
		//Create the client.
		client = new Client();
		
		//Register the packet object.
		client.getKryo().register(NetworkPack.class);
		client.getKryo().register(Down.class);
		client.getKryo().register(Up.class);
		client.getKryo().register(Left.class);
		client.getKryo().register(Right.class);

		//Start the client
		client.start();
		//The client MUST be started before connecting can take place.
		
		//Connect to the server - wait 5000ms before failing.
		try {
			client.connect(5000, ip, tcpPort, udpPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Add a listener
				client.addListener(new ClientProgram());
				
				System.out.println("Connected! The client program is now waiting for a packet...\n");
				
				//This is here to stop the program from closing before we receive a message.
				while(!gameover){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				System.out.println("Client will now exit.");
				System.exit(0);
			}
	
	public void received(Connection c, Object p){
		//Is the received packet the same class as PacketMessage.class?
		if(p instanceof NetworkPack){
			//Cast it, so we can access the message within.
			NetworkPack packet = (NetworkPack) p;
			//UPDATE EVERYTHING FOR CLIENT...
			/*DFVR.player.setBodyLocation(packet.serverX, packet.serverY);
			DFVR.opp.setBodyLocation(packet.clientX, packet.clientY);*/
			System.out.println("received a message from the host:");
		}
		else if(p instanceof Up){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Up");
			DFVR.player.getBody().applyForceToCenter(0f,10f,true);
			
		}
		else if(p instanceof Down){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Down");
			DFVR.player.getBody().applyForceToCenter(0f,-10f,true);
		}
		else if(p instanceof Right){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Right");
			DFVR.player.getBody().applyForceToCenter(10f,0f,true);
		}
		else if(p instanceof Left){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Left");
			DFVR.player.getBody().applyForceToCenter(-10f,0f,true);
		}
	}
	
	public static void SendUp(){
		Up pd = new Up();
		client.sendTCP(pd);
		//DFVR.opp.getBody().setLinearVelocity(DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y+1f);
		DFVR.opp.getBody().applyForceToCenter(0f,10f,true);
	}

	public static void SendDown(){
		Down pd = new Down();
		client.sendTCP(pd);
		//DFVR.opp.getBody().setLinearVelocity(DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y-1f);
		DFVR.opp.getBody().applyForceToCenter(0f,-10f,true);
	}
	
	public static void SendRight(){
		Right pd = new Right();
		client.sendTCP(pd);
		//DFVR.opp.getBody().setLinearVelocity(1f+DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y);
		DFVR.opp.getBody().applyForceToCenter(10f,0f,true);
	}
	
	public static void SendLeft(){
		Left pd = new Left();
		client.sendTCP(pd);
		//DFVR.opp.getBody().setLinearVelocity(-1f+DFVR.opp.getBody().getLinearVelocity().x, DFVR.opp.getBody().getLinearVelocity().y);
		DFVR.opp.getBody().applyForceToCenter(-10f,0f,true);
	}
	
	/*static Network network = new Network();
	
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
		
		this.getKryo().register(NetPack.class);
		
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
	
	*/
}
