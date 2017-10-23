package com.ballthrower;

import com.ballthrower.communication.BluetoothCommunicator;
import com.ballthrower.communication.Communicator;
import com.ballthrower.communication.PacketHandler;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.RequestTargetPacket;
import com.ballthrower.communication.packets.RotateRequestPacket;
import lejos.nxt.*;

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

            // Request target
            communicator.sendPacket(new RequestTargetPacket());

            // Receive answer to request
            Packet requestAnswer = communicator.receivePacket();
            if (requestAnswer.getId() == PacketHandler.PacketIds.RotateRequest.asByte())
            {
                LCD.drawString("Float: " + ((RotateRequestPacket)requestAnswer).getDegreesToRotate(), 0, 3);
                NXTMotor motor = new NXTMotor(MotorPort.C);
                motor.resetTachoCount();
                motor.setPower(30);
                motor.forward();
                while (motor.getTachoCount() < ((RotateRequestPacket) requestAnswer).getDegreesToRotate());
                motor.stop();
            }
        }
    }
}


/*            Packet packet = communicator.receivePacket();

            if (packet instanceof EnginePowerPacket)
                new NXTMotor(MotorPort.A).setPower(((EnginePowerPacket) packet).getEnginePower());
            else if (packet instanceof PingPacket)
            {
                // In case of the ping packet, we simply return the same packet
                Sound.buzz();
                communicator.sendPacket(packet);
            }*/