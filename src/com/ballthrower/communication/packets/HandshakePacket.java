package com.ballthrower.communication.packets;

import com.ballthrower.Robot;
import com.ballthrower.communication.Connection;

import java.io.IOException;

public class HandshakePacket extends Packet
{
    private short _validationToken;

    public HandshakePacket()
    {
        _validationToken = (short) Robot.getInstance().Random.nextInt(Short.MAX_VALUE);
    }

    public short getValidationToken()
    {
        return this._validationToken;
    }

    @Override
    public void constructFromConnection(Connection connection) throws IOException
    {
        this._validationToken = connection.getInputStream().readShort();
    }

    @Override
    public void writeToConnection(Connection connection) throws IOException
    {
        connection.getOutputStream().writeShort(_validationToken);
    }

    public boolean isValidReply(Packet other)
    {
        return other.getId() == PacketIds.Handshake && ((HandshakePacket) other).getValidationToken() == this.getValidationToken();
    }

    @Override
    public PacketIds getId()
    {
        return PacketIds.Handshake;
    }
}
