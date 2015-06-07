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
		DFVR.gamestate=2;
		System.out.println("Game is started");
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
		
		pM.setGamestate(DFVR.gamestate);
		
		pM.setServerX(DFVR.player.getBodyVector2().x);
		pM.setServerY(DFVR.player.getBodyVector2().y);
		pM.setClientX(DFVR.opp.getBodyVector2().x);
		pM.setClientY(DFVR.opp.getBodyVector2().y);
		
		pM.setClientPoint(DFVR.clientPoint);
		pM.setServerPoint(DFVR.serverPoint);
		
		pM.setStackServer(DFVR.player.getStack());
		pM.setStackServer(DFVR.player.getStack());
		server.sendToAllTCP(pM);
	}
	
	
	
	public void received(Connection c, Object p){
		//Is the received packet the same class as PacketMessage.class?
		if(p instanceof Up){
			DFVR.opp.setMove("Up");			
		}
		else if(p instanceof Down){
			DFVR.opp.setMove("Down");		
		}
		else if(p instanceof Right){
			DFVR.opp.setMove("Right");
			}
		else if(p instanceof Left){
			DFVR.opp.setMove("Left");
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
		DFVR.player.setMove("Up");
	}

	public static void SendDown(){
		DFVR.player.setMove("Down");
	}
	
	public static void SendRight(){
		DFVR.player.setMove("Right");
	}
	
	public static void SendLeft(){
		DFVR.player.setMove("Left");
	}
}