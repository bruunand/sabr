package com.ballthrower;

import com.ballthrower.communication.BluetoothConnection;
import com.ballthrower.communication.Connection;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.targeting.DistanceCalculator;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Connection connection = new BluetoothConnection();
		connection.awaitConnection();

		while (true)
        {
            Button.ENTER.waitForPressAndRelease();

            // Request target information
            connection.sendPacket(new TargetInfoRequestPacket());

            // Receive packet with target information
            Packet receivedPacket = connection.receivePacket();
            if (receivedPacket.getId() != PacketIds.TargetDirectionRequest)
                break;

            TargetInfoRequestPacket infoPacket = ((TargetInfoRequestPacket)receivedPacket);
            DistanceCalculator calc = new DistanceCalculator();
            LCD.clear();
            LCD.drawString("Distance:" + calc.calculateDistance(infoPacket.getTargetBoxInfo()), 0, 0);
        }
    }
}