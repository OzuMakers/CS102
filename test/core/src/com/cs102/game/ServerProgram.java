package com.cs102.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.cs102.game.*;

public class ServerProgram extends Listener implements Runnable{

	static Server server;
	static final int port = 27960;
	static Map<Integer, Player> players = new HashMap<Integer, Player>();
	
/*	public static void main(String[] args) throws IOException{
		server = new Server();
		server.getKryo().register(NetPack.class);
		server.bind(port, port);
		server.start();
		server.addListener(new ServerProgram());
		System.out.println("The server is ready");
	}
*/	
	public void connected(Connection c){
		NetPack	pd = new NetPack();
		pd.OppPlayerX=DFVR.player.getBodyVector2().x;
		pd.OppPlayerY=DFVR.player.getBodyVector2().y;
		server.sendToTCP(c.getID(), pd);
		System.out.println("Connection received.");
	}
	
	public void received(Connection c, Object o){
		if(o instanceof NetPack){
			NetPack pd = (NetPack) o;
			DFVR.opp.setBodyLocation(pd.OppPlayerX,pd.OppPlayerX);

			pd.OppPlayerX = DFVR.player.getBodyVector2().x;
			pd.OppPlayerX = DFVR.player.getBodyVector2().y;
			
			server.sendToAllExceptUDP(c.getID(), pd);
			System.out.println("received and sent an update X packet");
			
		}
	}
	
	public void disconnected(Connection c){
		System.out.println("Connection is dropped.");
	}

	@Override
	public void run() {
		server = new Server();
		server.getKryo().register(NetPack.class);
		try {
			server.bind(port, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();
		server.addListener(new ServerProgram());
		System.out.println("The server is ready");
	}
}
