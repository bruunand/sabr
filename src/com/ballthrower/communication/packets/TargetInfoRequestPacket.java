package com.ballthrower.communication.packets;

import com.ballthrower.communication.PacketHandler;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TargetInfoRequestPacket extends Packet
{
    /* Frame information */
    private short _frameWidth;

    /* Box information */
    private short[] _x;//, _y;
    private float[] _width, _height;

    @Override
    public void constructFromStream(DataInputStream stream) throws IOException
    {
        // Read the width of the frame, used to calculate the middle of the image
        this._frameWidth = stream.readShort();

        // Read number of box instances
        byte numBoxInstances = stream.readByte();

        // Initialize arrays based on number of box instances
        this._x = new short[numBoxInstances];
        this._width = new float[numBoxInstances];
        this._height = new float[numBoxInstances];

        // Read box instances
        for (byte i = 0; i < numBoxInstances; i++)
        {
            // Read start position (just x)
            this._x[i] = stream.readShort();

            // Read box size
            this._width[i] = stream.readFloat();
            this._height[i] = stream.readFloat();
        }
    }

    public short getFrameWidth()
    {
        return this._frameWidth;
    }

    public byte getBoxInstanceAmount()
    {
        return (byte) this._x.length;
    }

    public short getBoxX(byte index)
    {
        return this._x[index];
    }

    public float getBoxWidth(byte index)
    {
        return this._width[index];
    }

    public float getBoxHeight(byte index)
    {
        return this._height[index];
    }

    @Override
    public void writeToStream(DataOutputStream stream) throws IOException
    {
    }

    @Override
    public PacketHandler.PacketIds getId()
    {
        return PacketHandler.PacketIds.TargetDirectionRequest;
    }
}