package com.ballthrower.communication.packets;

import com.ballthrower.communication.Connection;
import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;

import java.io.DataInputStream;
import java.io.IOException;

public class TargetInfoRequestPacket extends Packet
{
    private TargetContainer _boxInfo;

    @Override
    public void constructFromConnection(Connection connection) throws IOException
    {
        DataInputStream stream = connection.getInputStream();

        // Read the width of the frame, used to calculate the middle of the image
        short frameWidth = stream.readShort();

        // Read the number of samples and create target box info object
        byte numBoxSamples = stream.readByte();
        this._boxInfo = new TargetContainer(numBoxSamples);
        this._boxInfo.setFrameWidth(frameWidth);

        // Get target box samples
        for (byte i = 0; i < numBoxSamples; i++)
        {
            // read info from data stream
            short xPos = stream.readShort();
            short width = stream.readShort();
            short height = stream.readShort();

            this._boxInfo.setTarget(i, new TargetBox(height, width, xPos));
        }
    }

    public ITargetContainer getTargetBoxInfo()
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