package com.ballthrower;

import com.ballthrower.communication.BluetoothCommunicator;
import com.ballthrower.communication.Communicator;
import com.ballthrower.communication.PacketHandler;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Communicator communicator = new BluetoothCommunicator();
		communicator.awaitConnection();

		while (true)
        {
            Button.ENTER.waitForPressAndRelease();

            // Request target information
            communicator.sendPacket(new TargetInfoRequestPacket());

            // Receive packet with target information
            Packet receivedPacket = communicator.receivePacket();
            if (receivedPacket.getId() != PacketHandler.PacketIds.TargetDirectionRequest)
                break;

            TargetInfoRequestPacket infoPacket = ((TargetInfoRequestPacket)receivedPacket);
            DistanceCalculator calc = new DistanceCalculator();
            LCD.clear();
            LCD.drawString("Distance:" + calc.calculateDistance(infoPacket.getTargetBoxInfo()), 0, 0);
        }
    }
}