package com.cs102.game;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.cs102.networkpacks.*;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class ClientProgram extends Listener implements Runnable{
	public static Array<Vector2> transfer1 = new Array<Vector2>();
	public static Array<Vector2> transfer2= new Array<Vector2>();
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
				DFVR.gamestate=2;
				System.out.println("Game is started");
				//This is here to stop the program from closing before we receive a message.
				while(!gameover){
				}
				
				System.out.println("Client will now exit.");
				System.exit(0);
			}

	
	public void received(Connection c, Object p){
		//Is the received packet the same class as PacketMessage.class?
		if(p instanceof NetworkPack){
			NetworkPack packet = (NetworkPack) p;
			Vector2 a = new Vector2(packet.getServerX(), packet.getServerY());
			Vector2 b = new Vector2(packet.getClientX(), packet.getClientY());
			DFVR.gamestate=((NetworkPack) p).getGamestate();
			DFVR.serverPoint=((NetworkPack) p).getServerPoint();
			DFVR.clientPoint=((NetworkPack) p).getClientPoint();
			 transfer1.add(a);
			 transfer2.add(b);			 
		}
	}
	
	public static void SendUp(){
		Up pd = new Up();
		client.sendTCP(pd);
	}

	public static void SendDown(){
		Down pd = new Down();
		client.sendTCP(pd);
	}
	
	public static void SendRight(){
		Right pd = new Right();
		client.sendTCP(pd);
	}
	
	public static void SendLeft(){
		Left pd = new Left();
		client.sendTCP(pd);
	}
}
