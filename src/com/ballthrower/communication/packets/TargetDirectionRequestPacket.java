package com.ballthrower.communication.packets;

import com.ballthrower.communication.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TargetDirectionRequestPacket extends Packet
{
    private short _frameWidth;

    @Override
    public void constructFromStream(DataInputStream stream) throws IOException
    {
        this._frameWidth = stream.readShort();
        // Read bounding box
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