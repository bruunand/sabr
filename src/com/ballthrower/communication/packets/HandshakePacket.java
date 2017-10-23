package com.ballthrower.communication.packets;

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

    @Override
    public byte getId()
    {
        return 0x0;
    }
}