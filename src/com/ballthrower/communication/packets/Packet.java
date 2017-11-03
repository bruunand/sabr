package com.ballthrower.communication.packets;

import com.ballthrower.communication.Connection;
import com.ballthrower.exceptions.UnknownPacketException;

import java.io.IOException;

public abstract class Packet
{
	public abstract void constructFromConnection(Connection connection) throws IOException;
	public abstract void writeToConnection(Connection connection) throws IOException;

	public abstract PacketIds getId();

	public static Packet instantiateFromId(PacketIds id) throws UnknownPacketException
	{
		switch (id)
		{
			case Handshake:
				return new HandshakePacket();
			case TargetDirectionRequest:
				return new TargetInfoRequestPacket();
			default:
				throw new UnknownPacketException("Packet Id " + id + " is unknown.");
		}
	}
}
