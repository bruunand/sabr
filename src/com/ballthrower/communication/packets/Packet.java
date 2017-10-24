package com.ballthrower.communication.packets;

import com.ballthrower.communication.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet
{
	public abstract void constructFromStream(DataInputStream stream) throws IOException;
	public abstract void writeToStream(DataOutputStream stream) throws IOException;

	public abstract PacketHandler.PacketIds getId();
}
