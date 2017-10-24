package com.ballthrower.communication.packets;

import com.ballthrower.communication.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class HandshakePacket extends Packet
{
    private short _validationToken;

    public HandshakePacket()
    {
        _validationToken = (short) new Random().nextInt(Short.MAX_VALUE);
    }

    public short getValidationToken()
    {
        return this._validationToken;
    }

    @Override
    public void constructFromStream(DataInputStream stream) throws IOException
    {
        this._validationToken = stream.readShort();
    }

    @Override
    public void writeToStream(DataOutputStream stream) throws IOException
    {
        stream.writeShort(_validationToken);
    }

    public boolean isValidReply(Packet other)
    {
        return other.getId() == PacketHandler.PacketIds.Handshake &&
                ((HandshakePacket) other).getValidationToken() == this.getValidationToken();

    }

    @Override
    public PacketHandler.PacketIds getId()
    {
        return PacketHandler.PacketIds.Handshake;
    }
}