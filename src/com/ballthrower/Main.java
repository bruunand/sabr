package com.ballthrower;

import com.ballthrower.communication.BluetoothCommunicator;
import com.ballthrower.communication.Communicator;
import com.ballthrower.communication.PacketHandler;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.ITargetBoxInfo;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Communicator communicator = new BluetoothCommunicator();
		communicator.awaitConnection();

        DistanceCalculator calc = new DistanceCalculator();
        DirectionCalculator direction = new DirectionCalculator();

		while (true)
        {
            int result = Button.waitForAnyPress();
            if (result != Button.ID_ENTER)
                break;

            // Request target information
            communicator.sendPacket(new TargetInfoRequestPacket());

            // Receive packet with target information
            Packet receivedPacket = communicator.receivePacket();
            if (receivedPacket.getId() != PacketHandler.PacketIds.TargetDirectionRequest)
                break;

            ITargetBoxInfo targetInformation = ((TargetInfoRequestPacket)receivedPacket).getTargetBoxInfo();
            LCD.clear();
            LCD.drawString("Distance:" + calc.calculateDistance(targetInformation), 0, 0);
            LCD.drawString("Direction:" + direction.calculateDirection(targetInformation), 0, 1);
            LCD.drawString("Middle:" + targetInformation.getFrameWidth(), 0, 2);
        }
    }
}