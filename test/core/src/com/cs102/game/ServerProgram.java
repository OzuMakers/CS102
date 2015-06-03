package com.cs102.game;

import java.io.IOException;

import com.badlogic.gdx.Input;
import com.cs102.networkpacks.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class ServerProgram extends Listener implements Runnable{
	
	public static boolean gameover=false;
	//Server object
	static Server server;
	//Ports to listen on
	static int udpPort = 27960, tcpPort = 27960;
	
	//This is run when a connection is received!
	public void connected(Connection c){
		System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
		//Create a message packet.
		NetworkPack packetMessage = new NetworkPack();
		//Assign the message text.
		
		//Send the message
		c.sendTCP(packetMessage);
		//Alternatively, we could do:
		//c.sendUDP(packetMessage);
		//To send over UDP.
	}
	
	public void sendPack(){
		NetworkPack pM = new NetworkPack();
		//UPDATE PACK HERE
		pM.serverX=DFVR.player.getBodyVector2().x;
		pM.serverY=DFVR.player.getBodyVector2().y;
		pM.clientX=DFVR.opp.getBodyVector2().x;
		pM.clientY=DFVR.opp.getBodyVector2().y;
		server.sendToAllTCP(pM);
	}
	
	
	
	public void received(Connection c, Object p){
		//Is the received packet the same class as PacketMessage.class?
		if(p instanceof Up){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Up");
			DFVR.opp.getBody().applyForceToCenter(0f,10f,true);
			
		}
		else if(p instanceof Down){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Down");
			DFVR.opp.getBody().applyForceToCenter(0f,-10f,true);
		}
		else if(p instanceof Right){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Right");
			DFVR.opp.getBody().applyForceToCenter(10f,0f,true);
		}
		else if(p instanceof Left){
			//APPLY CHANGE ON CLIENT BODY
			System.out.println("Left");
			DFVR.opp.getBody().applyForceToCenter(-10f,0f,true);
		}
	}
	//This is run when a client has disconnected.
	public void disconnected(Connection c){
		System.out.println("A client disconnected!");
		gameover=true;
	}

	@Override
	public void run() {
		System.out.println("Creating the server...");
		//Create the server
		server = new Server();
		
		//Register a packet class.
		server.getKryo().register(NetworkPack.class);
		server.getKryo().register(Down.class);
		server.getKryo().register(Up.class);
		server.getKryo().register(Left.class);
		server.getKryo().register(Right.class);
		//We can only send objects as packets if they are registered.
		
		//Bind to a port
		try {
			server.bind(tcpPort, udpPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Start the server
		server.start();
		
		//Add the listener
		server.addListener(new ServerProgram());
		
		System.out.println("Server is operational!");
		
		while(!gameover){
			try {
				sendPack();
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void SendUp(){
		Up pd = new Up();
		server.sendToAllTCP(pd);
		DFVR.player.getBody().applyForceToCenter(0f,10f,true);
	}

	public static void SendDown(){
		Down pd = new Down();
		server.sendToAllTCP(pd);
		DFVR.player.getBody().applyForceToCenter(0f,-10f,true);
	}
	
	public static void SendRight(){
		Right pd = new Right();
		server.sendToAllTCP(pd);
		DFVR.player.getBody().applyForceToCenter(10f,0f,true);
	}
	
	public static void SendLeft(){
		Left pd = new Left();
		server.sendToAllTCP(pd);
		DFVR.player.getBody().applyForceToCenter(-10f,0f,true);
	}
}