package com.ballthrower.tools;

import com.ballthrower.communication.Connection;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.movement.aiming.Rotator;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.ITargetContainer;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;

public class RotationCalibrator
{
    private final static float DegreesPerTurn = 1f;
    private final static float MaxPixelDistance = 1.5f;

    private final Connection _connection;

    public RotationCalibrator(Connection connection)
    {
        this._connection = connection;
    }

    private ITargetContainer getTargetInformation()
    {
        // Request packet
        _connection.sendPacket(new TargetInfoRequestPacket());

        // Receive packet with target information
        Packet receivedPacket = _connection.receivePacket();
        if (receivedPacket.getId() != PacketIds.TargetDirectionRequest)
            return null;

        return ((TargetInfoRequestPacket)receivedPacket).getTargetBoxInfo();
    }

    /* Searches for a cup and returns its degrees per pixel. */
    public float calibrate()
    {
        Rotator rotator = new Rotator(MotorPort.C);
        DirectionCalculator directionCalculator = new DirectionCalculator();
        float startDistance = directionCalculator.calculateMeanPixelDistance(getTargetInformation());

        float degreesTurned = 0f;
        while (Math.abs(directionCalculator.calculateDirection(getTargetInformation())) > MaxPixelDistance)
        {
            rotator.turnDegrees(DegreesPerTurn);
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {

            }
            degreesTurned += DegreesPerTurn;
        }

        LCD.clear();
        LCD.drawString("S: " + startDistance, 0, 0);
        LCD.drawString("Degrees: " + degreesTurned, 0, 1);

        return degreesTurned / startDistance;
    }
}
