package com.cs102.game;

import java.io.IOException;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class Network extends Listener {

	Client client;
	String ip = "localhost";
	int port = 27960;

	public void connect(){
		client = new Client();
		client.getKryo().register(NetPack.class);
		client.addListener(this);

		client.start();
		try {
			client.connect(5000, ip, port, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//information to be delivered, such as acceleration, direction and position
	public void received(Connection c, Object o){
		if(o instanceof NetPack){
			NetPack pd = (NetPack) o;
			DFVR.opp.setBodyLocation(pd.OppPlayerX,pd.OppPlayerX);
			System.out.println("Network.received");
		}
	}
}