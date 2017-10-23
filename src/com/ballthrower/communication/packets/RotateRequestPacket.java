package com.ballthrower.communication.packets;

import lejos.nxt.Sound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RotateRequestPacket extends Packet
{
    private float _degreesToRotate = 0;

    public float getDegreesToRotate()
    {
        return this._degreesToRotate;
    }

    public void setDegreesToRotate(float degrees)
    {
        this._degreesToRotate = degrees;
    }

    @Override
    public void constructFromStream(DataInputStream stream) throws IOException
    {
        this.setDegreesToRotate(stream.readFloat());
    }

    @Override
    public void writeToStream(DataOutputStream stream) throws IOException
    {
    }

    @Override
    public byte getId()
    {
        return 0x2;
    }
}