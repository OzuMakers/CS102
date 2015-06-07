package com.cs102.networkpacks;

import java.util.Stack;

import com.cs102.game.Trace;

public class NetworkPack {
private float serverX=0;
private float serverY=0;
private float clientX=0;
private float clientY=0;
private int gamestate;
private float serverPoint;
private float clientPoint;
private Stack<Trace> traceStackServer;
private Stack<Trace> traceStackClient;

public Stack<Trace> getStackServer(){
	return traceStackServer;
}

public void setStackServer(Stack<Trace> tracePointer){
	traceStackServer=tracePointer;
}

public Stack<Trace> getStackClient(){
	return traceStackServer;
}

public void setStackClient(Stack<Trace> tracePointer){
	traceStackServer=tracePointer;
}

public  float getServerX() {
	return serverX;
}
public void setServerX(float serverX) {
	this.serverX = serverX;
}
public float getServerY() {
	return serverY;
}
public void setServerY(float serverY) {
	this.serverY = serverY;
}
public float getClientX() {
	return clientX;
}
public void setClientX(float clientX) {
	this.clientX = clientX;
}
public float getClientY() {
	return clientY;
}
public void setClientY(float clientY) {
	this.clientY = clientY;
}
public int getGamestate() {
	return gamestate;
}
public void setGamestate(int gamestate) {
	this.gamestate = gamestate;
}
public float getServerPoint() {
	return serverPoint;
}
public void setServerPoint(float serverPoint) {
	this.serverPoint = serverPoint;
}
public float getClientPoint() {
	return clientPoint;
}
public void setClientPoint(float clientPoint2) {
	this.clientPoint = clientPoint2;
}

}
