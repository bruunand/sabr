package com.ballthrower;

import com.ballthrower.communication.BluetoothConnection;
import com.ballthrower.communication.Connection;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.movement.MovementController;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.ITargetBoxInfo;
import com.ballthrower.tools.RotationCalibrator;
import lejos.nxt.*;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Connection connection = new BluetoothConnection();
		connection.awaitConnection();

        DistanceCalculator calc = new DistanceCalculator();
        DirectionCalculator direction = new DirectionCalculator();
        MovementController controller = new MovementController(new NXTRegulatedMotor(MotorPort.C), null);

		while (true)
        {
            // Request target information
            connection.sendPacket(new TargetInfoRequestPacket());

            // Receive packet with target information
            Packet receivedPacket = connection.receivePacket();
            if (receivedPacket.getId() != PacketIds.TargetDirectionRequest)
                break;
            ITargetBoxInfo targetInformation = ((TargetInfoRequestPacket)receivedPacket).getTargetBoxInfo();
            LCD.clear();
            float degrees = direction.calculateDirection(targetInformation);
            LCD.drawString("Direction:" + degrees, 0, 1);
            if (Math.abs(degrees) > 3f)
                controller.getRotator().turnDegrees(degrees);
            else
                LCD.drawString("Distance:" + calc.calculateDistance(targetInformation), 0, 0);

            try
            {
                Thread.sleep(250);
            } catch (InterruptedException e)
            {

            }
        }
    }
}