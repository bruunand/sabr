package com.ballthrower.communication.packets;

import com.ballthrower.communication.Connection;
import com.ballthrower.targeting.ITargetBoxInfo;
import com.ballthrower.targeting.TargetBoxInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class TargetInfoRequestPacket extends Packet
{
    private TargetBoxInfo _boxInfo;

    @Override
    public void constructFromConnection(Connection connection) throws IOException
    {
        DataInputStream stream = connection.getInputStream();

        // Read the width of the frame, used to calculate the middle of the image
        short frameWidth = stream.readShort();

        // Read the number of samples and create target box info object
        byte numBoxSamples = stream.readByte();
        this._boxInfo = new TargetBoxInfo(numBoxSamples);
        this._boxInfo.setFrameWidth(frameWidth);

        // Read box samples
        for (byte i = 0; i < numBoxSamples; i++)
        {
            // Read x position
            this._boxInfo.setXTopPos(i, stream.readShort());

            // Read box size
            this._boxInfo.setBoxWidth(i, stream.readFloat());
            this._boxInfo.setBoxHeight(i, stream.readFloat());
        }
    }

    public ITargetBoxInfo getTargetBoxInfo()
    {
        return this._boxInfo;
    }

    @Override
    public void writeToConnection(Connection connection) throws IOException
    {
    }

    @Override
    public PacketIds getId()
    {
        return PacketIds.TargetDirectionRequest;
    }
}