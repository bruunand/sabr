package com.ballthrower.communication.packets;

import com.ballthrower.communication.Connection;

import java.io.IOException;

public class DebugPacket extends Packet
{
    private final String _message;

    public DebugPacket(String message)
    {
        this._message = message;
    }

    @Override
    public void constructFromConnection(Connection connection) throws IOException
    {
    }

    @Override
    public void writeToConnection(Connection connection) throws IOException
    {
        byte[] messageBytes = this._message.getBytes("UTF-8");
        connection.getOutputStream().writeShort(messageBytes.length);
        connection.getOutputStream().write(messageBytes);
    }

    @Override
    public PacketIds getId()
    {
        return PacketIds.Debug;
    }
}