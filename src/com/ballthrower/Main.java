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
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Connection connection = new BluetoothConnection();
		connection.awaitConnection();

        DistanceCalculator calc = new DistanceCalculator();
        DirectionCalculator direction = new DirectionCalculator();
        MovementController controller = new MovementController(MotorPort.C, new MotorPort[]{MotorPort.A, MotorPort.B});

        Button.ENTER.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
            }

            public void buttonReleased(Button b)
            {
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
                        controller.turnDegrees(degrees);
                    else
                    {
                        LCD.drawString("Distance:" + calc.calculateDistance(targetInformation), 0, 4);
                        controller.shootDistance(calc.calculateDistance(targetInformation));
                        break;
                    }


                    try
                    {
                        Thread.sleep(250);
                    } catch (InterruptedException e)
                    {

                    }
                }
            }
        });
        Button.ESCAPE.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                System.exit(1);
            }

            public void buttonReleased(Button b)
            {
            }
        });
        while(true);
    }
}