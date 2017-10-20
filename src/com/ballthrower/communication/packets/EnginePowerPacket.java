package com.ballthrower.communication.packets;

import java.io.DataInputStream;
import java.io.IOException;

public class EnginePowerPacket extends Packet
{
    private short _enginePower = 0;

    public short getEnginePower()
    {
        return this._enginePower;
    }

    public void setEnginePower(short power)
    {
        this._enginePower = power;
    }

    @Override
    public void constructFromStream(DataInputStream stream) throws IOException
    {
        setEnginePower(stream.readShort());
    }

    @Override
    public void writeToStream(DataInputStream stream) throws IOException
    {

    }
}