package com.ballthrower.communication;

public abstract class Communicator
{
	public abstract void awaitConnection();
	public abstract void closeConnection();

	public abstract boolean isAlive();

	public abstract Packet receivePacket();
	public abstract void sendPacket(Packet packet);
}
